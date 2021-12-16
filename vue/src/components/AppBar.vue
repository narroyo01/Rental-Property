<template>
  <v-app-bar app clipped-left color="teal darken-4">
    <v-app-bar-nav-icon
      color="grey lighten-5"
      @click="$emit('toggleDrawer')"
    ></v-app-bar-nav-icon>
    <v-img
      @click="navHome()"
      style="cursor: pointer"
      class="ms-3"
      max-height="83"
      max-width="83"
      src="../assets/waystone.png"
      contain
    ></v-img>
    <!-- <v-list-item class="grey--text text--lighten-5">
      <v-list-item-content>
        <v-list-item-title class="text-h6">
          <span
            @click="navHome()"
            style="display: inline-block; cursor: pointer"
            >Waystone</span
          >
        </v-list-item-title>
        <v-list-item-subtitle
          class="grey--text text--lighten-5"
          @click="navHome()"
        >
          <span
            @click="navHome()"
            style="display: inline-block; cursor: pointer"
          >
            Rental Property Manager</span
          >
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item> -->
    <v-list-item />
    <v-menu v-if="$store.state.user.username" left bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn class="grey--text text--lighten-5" text v-bind="attrs" v-on="on">
          {{ $store.state.user.username }} {{ role }}
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          @click="
            () => {
              this.$router.push('/logout');
            }
          "
        >
          <v-list-item-title>Logout</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
export default {
  name: "AppBar",
  methods: {
    navHome() {
      if (this.$route.path != "/") {
        this.$router.push("/");
      }
    },
  },
  computed: {
    role() {
      return this.$store.state.user.authorities[0].name
        ? "(" +
            this.$store.state.user.authorities[0].name.split("ROLE_")[1] +
            ")"
        : "";
    },
  },
};
</script>

<style scoped>
</style>