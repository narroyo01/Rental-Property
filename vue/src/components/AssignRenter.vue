<template>
  <v-form
    @submit.prevent="$emit('assignTenant', getUserIdByUsername(selectedUser))"
  >
    <div v-if="users === null">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <v-autocomplete :items="usernames" v-model="selectedUser"></v-autocomplete>
    </div>
    <v-btn color="primary" elevation="2" type="submit">Assign</v-btn>
  </v-form>
</template>

<script>
import userService from "../services/UserService";

export default {
  name: "AssignRenter",
  props: ["renter"],
  data() {
    return {
      users: null,
      selectedUser: this.renter,
    };
  },
  methods: {
    getUserIdByUsername(username) {
      return this.users.find((user) => {
        return user.username === username;
      }).id;
    },
  },
  computed: {
    usernames() {
      let usernames = [];
      this.users.forEach((user) => {
        usernames.push(user.username);
      });
      return usernames;
    },
  },
  created() {
    userService.getUsersByRole("ROLE_USER").then((response) => {
      if (response.status === 200) {
        this.users = response.data;
      }
    });
  },
};
</script>

<style scoped>
</style>