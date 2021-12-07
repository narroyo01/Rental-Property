<template>
  <v-list dense nav>
    <v-list-item
      v-for="nav in activeNavs"
      :key="nav.title"
      link
      @click="navTo(nav.to)"
    >
      <v-list-item-icon>
        <v-icon>{{ nav.icon }}</v-icon>
      </v-list-item-icon>
      <v-list-item-content>
        <v-list-item-title>{{ nav.title }}</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
  </v-list>
</template>

<script>
export default {
  name: "NavDrawer",
  data() {
    return {
      navs: [
        { title: "Available Properties", icon: "mdi-home-group", to: "/login" },
        {
          title: "Request Maintenance",
          icon: "mdi-account-wrench",
          to: "/login",
        },
   //     { title: "Admin Portal", icon: "mdi-shield-account", to: "/login" },
        { title: "Add Property", icon: "mdi-home-group-plus", to: "/add-property"},
        { title: "Login", icon: "mdi-login", to: "/login" },
      ],
    };
  },
  computed: {
    activeNavs() {
      return this.navs.filter((nav) => {
        if (nav.title === "Login" && this.$store.state.user.username) return false;
        if (nav.title === "Request Maintenance" && !this.$store.state.user.username) return false;
        if (nav.title === "Add Property" && ! this.$store.state.user.username) {
          return false;
        } else if (nav.title === "Add Property" && this.$store.state.user.username) {
          if (! (this.$store.state.user.authorities[0].name === "ROLE_ADMIN" || this.$store.state.user.authorities[0].name === "ROLE_LANDLORD")) {
            return false;
          }
        }
        return true;
      });
    },
  },
  methods: {
    navTo(path) {
      if (this.$route.path != path) {
        this.$router.push(path);
      }
    },
  },
};
</script>