<template>
  <v-app id="app">
    <app-bar @toggleDrawer="drawer = !drawer" />

    <v-navigation-drawer app v-model="drawer" clipped src="https://images.nightcafe.studio/jobs/JzRW1podi1MqvXhNhoCt/JzRW1podi1MqvXhNhoCt.jpg?tr=w-1600,c-at_max">
      <nav-drawer />
    </v-navigation-drawer>

    <!-- Sizes your content based upon application components -->
    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid>
        <!-- If using vue-router -->
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer app>
      <!-- -->
    </v-footer>
  </v-app>
</template>

<script>
import NavDrawer from "./components/NavDrawer.vue";
import AppBar from "./components/AppBar.vue";

export default {
  components: { NavDrawer, AppBar },
  name: "App",
  data() {
    return {
      drawer: null,
    };
  },
};
</script>

<style>
  #app{
    background-color: #EEEEEE;
  }
</style>

