<template>
  <v-app-bar app clipped-left>
    <v-app-bar-nav-icon @click="$emit('toggleDrawer')"></v-app-bar-nav-icon>
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title
          class="text-h6"
          @click="navHome()"
          style="display: inline-block; cursor: pointer"
        >
          Rental Property
        </v-list-item-title>
        <v-list-item-subtitle @click="navHome()">
          Manager
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-list-item />
    <v-menu v-if="$store.state.user.username" left bottom>
      <template v-slot:activator="{ on, attrs }">
        <v-btn text v-bind="attrs" v-on="on">
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