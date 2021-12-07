<template>
  <v-form ref="form" @submit.prevent="login">
    <v-container id="loginBox">
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
      <v-text-field
        :loading="loading"
        :rules="rules"
        label="Username"
        v-model="user.username"
      ></v-text-field>
      <v-text-field
        :loading="loading"
        :rules="rules"
        label="Password"
        v-model="user.password"
        type="password"
      ></v-text-field>
      <v-btn class="mt-4" elevation="2" color="primary" type="submit">Login</v-btn>
      <v-alert class="mt-6" type="error" v-if="invalidCredentials">
        Invalid username and password!
      </v-alert>
      <v-alert type="success" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </v-alert>
      <p class="mt-6">
        <router-link :to="{ name: 'register' }">Need an account?</router-link>
      </p>
    </v-container>
  </v-form>

  <!-- BELOW WAS STARTER CODE -->
  <!-- <div id="login" class="text-center">
      <form class="form-signin" @submit.prevent="login">
        <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
        <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>
        <div
          class="alert alert-success"
          role="alert"
          v-if="this.$route.query.registration"
        >
          Thank you for registering, please sign in.
        </div>
        <label for="username" class="sr-only">Username</label>
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
        <label for="password" class="sr-only">Password</label>
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
        <router-link :to="{ name: 'register' }">Need an account?</router-link>
        <button type="submit">Sign in</button>
      </form>
    </div> -->
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
      rules: [
        value => !!value || 'Required',
        value => (value || '').length <= 20 || 'Max 20 characters',
        value => (value || '').length > 2 || 'Min 3 characters',
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