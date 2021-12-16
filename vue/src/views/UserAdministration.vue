<template>
  <v-container id="container">
      <h2>User Administration</h2>
    <v-form @submit.prevent="update">
      <v-select
        label="Select Role"
        @change="getUsers"
        :items="roles"
        v-model="selectedRole"
        hint="Choose a role to select users from"
        persistent-hint
        class="mt-6"
      ></v-select>
      <v-autocomplete
        v-if="users != null"
        :items="usernames"
        label="Select User"
        v-model="user"
        hint="Choose a user to change role"
        persistent-hint
        class="mt-6"
      ></v-autocomplete>
      <v-select
        v-if="user != null"
        label="Select Role"
        @change="getUsers"
        :items="rolesToPickFrom"
        v-model="newRole"
        hint="Choose the new role"
        persistent-hint
        class="mt-6"
      ></v-select>
      <v-alert class="mt-6" type="success" v-if="success">
        Users' role successfully updated
      </v-alert>
      <v-btn v-bind:disabled="!newRole" class="mt-6" color="primary" type="submit">Update</v-btn>
    </v-form>
  </v-container>
</template>

<script>
import userService from "../services/UserService.js";
export default {
  data() {
    return {
      name: "user-administration",
      users: null,
      roles: ["ROLE_USER", "ROLE_MAINTENANCE", "ROLE_LANDLORD", "ROLE_ADMIN"],
      selectedRole: null,
      user: null,
      newRole: null,
      success: false,
    };
  },
  methods: {
    getUsers() {
      this.users = null;
      userService.getUsersByRole(this.selectedRole).then((response) => {
        if (response.status === 200) {
          this.users = response.data;
          console.log(this.users);
          this.success = false;
        }
      });
    },
    update() {
      this.success = false;
      userService
        .updateUser({
          id: this.getUserIdByUsername(this.user),
          role: this.newRole,
        })
        .then((response) => {
          if (response.status === 200) {
            this.success = true;
            this.newRole = null;
            this.selectedRole = null;
            this.user = null;
            this.users = null;
          }
        });
    },
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
    rolesToPickFrom() {
      return this.roles.filter((role) => role != this.selectedRole);
    },
  },
};
</script>

<style scoped>
#container{
    max-width: 600px;
}
</style>