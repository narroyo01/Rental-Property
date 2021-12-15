<template>
  <v-form ref="form" @submit.prevent="login">
    <v-container id="loginBox" style="background-color:white">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <v-text-field
        :loading="loading"
        :rules="rulesUsername"
        label="Username"
        v-model="user.username"
      ></v-text-field>
      <v-text-field
        :loading="loading"
        :rules="rulesPassword"
        label="Password"
        v-model="user.password"
        type="password"
      ></v-text-field>
      <v-btn class="mt-4" elevation="2" color="primary" type="submit">Login</v-btn>
      <v-alert class="mt-6" type="error" v-if="invalidCredentials">
        Invalid username and password!
      </v-alert>
      <v-alert class="mt-6" type="success" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </v-alert>
      <p class="mt-6">
        <router-link :to="{ name: 'register' }">Need an account?</router-link>
      </p>
    </v-container>
  </v-form>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
      loading: false,
      rulesUsername: [
        value => !!value || 'Required',
        value => (value || '').length <= 20 || 'Max 20 characters',
        value => (value || '').length > 2 || 'Min 3 characters',
      ],
      rulesPassword: [
        value => !!value || 'Required',
        value => (value || '').length <= 80 || 'Max 80 characters',
        value => (value || '').length > 5 || 'Min 6 characters',
      ],
    };
  },
  methods: {
    login() {
      if (! this.$refs.form.validate()) return;
      this.loading = true;
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          this.loading = false;
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>

<style scoped>
#loginBox {
  max-width: 400px;
}
</style>