<template >
  <v-list dense nav>
    <v-list-item-group color="primary">
      <v-list-item
        class="item"
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
    </v-list-item-group>
  </v-list>
</template>

<script>
export default {
  name: "NavDrawer",
  data() {
    return {
      property: {
        propertyId: "",
      },
      navs: [
        {
          title: "Renter Dashboard",
          icon: "mdi-view-dashboard",
          to: "/dashboard",
        },
        { title: "Properties", icon: "mdi-home-group", to: "/property" },
        // {
        //   title: "Request Maintenance",
        //   icon: "mdi-account-wrench",
        //   to: "/login",
        // },
        {
          title: "My Maintenance Tasks",
          icon: "mdi-account-hard-hat",
          to: "/maintenance-request",
        },
        //     { title: "Admin Portal", icon: "mdi-shield-account", to: "/login" },
        {
          title: "Add Property",
          icon: "mdi-home-group-plus",
          to: "/add-property",
        },
        {
          title: "Assign Technician",
          icon: "mdi-clipboard-account-outline",
          to: "/maintenance-request/assign",
        },
        { title: "Login", icon: "mdi-login", to: "/login" },
        { title: "View Rent", icon: "mdi-currency-usd", to: "/transaction" },
        { title: "User Administration", icon: "mdi-account-edit", to: "/administration"}
      ],
    };
  },
  computed: {
    activeNavs() {
      return this.navs.filter((nav) => {
        if (
          nav.title === "Renter Dashboard" &&
          !this.$store.state.user.username
        )
          return false;

        if (nav.title === "Login" && this.$store.state.user.username)
          return false;

        if (
          nav.title === "Request Maintenance" &&
          !this.$store.state.user.username
        )
          return false;

        if (nav.title === "Add Property" && !this.$store.state.user.username) {
          return false;
        } else if (
          nav.title === "Add Property" &&
          this.$store.state.user.username
        ) {
          if (
            !(
              this.$store.state.user.authorities[0].name === "ROLE_ADMIN" ||
              this.$store.state.user.authorities[0].name === "ROLE_LANDLORD"
            )
          ) {
            return false;
          }
        }

        if (nav.title === "User Administration" && !this.$store.state.user.username) {
          return false;
        } else if (
          nav.title === "User Administration" &&
          this.$store.state.user.username
        ) {
          if (
            !(
              this.$store.state.user.authorities[0].name === "ROLE_ADMIN"
            )
          ) {
            return false;
          }
        }

        if (nav.title === "View Rent" && !this.$store.state.user.username) {
          return false;
        } else if (
          nav.title === "View Rent" &&
          this.$store.state.user.username
        ) {
          if (
            !(
              this.$store.state.user.authorities[0].name === "ROLE_ADMIN" ||
              this.$store.state.user.authorities[0].name === "ROLE_LANDLORD"
            )
          ) {
            return false;
          }
        }

        if (
          nav.title === "My Maintenance Tasks" &&
          !this.$store.state.user.username
        ) {
          return false;
        } else if (
          nav.title === "My Maintenance Tasks" &&
          this.$store.state.user.username
        ) {
          if (
            !(this.$store.state.user.authorities[0].name === "ROLE_MAINTENANCE")
          ) {
            return false;
          }
        }

        if (
          nav.title === "Assign Technician" &&
          !this.$store.state.user.username
        ) {
          return false;
        } else if (
          nav.title === "Assign Technician" &&
          this.$store.state.user.username
        ) {
          if (
            !(
              this.$store.state.user.authorities[0].name === "ROLE_ADMIN" ||
              this.$store.state.user.authorities[0].name === "ROLE_LANDLORD" ||
              this.$store.state.user.authorities[0].name === "ROLE_MAINTENANCE"
            )
          ) {
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

<style scoped>
.item {
  background-color: white;
  opacity: 90%;
}
</style>