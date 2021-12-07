<template>
  <v-form ref="form" @submit.prevent="register">
    <v-container id="registerBox">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
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
      <v-text-field
        :loading="loading"
        :rules="rulesPassword"
        label="Confirm Password"
        v-model="user.confirmPassword"
        type="password"
      ></v-text-field>
      <v-btn class="mt-4" elevation="2" color="primary" type="submit"
        >Register</v-btn
      >
      <v-alert class="mt-6" type="error" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </v-alert>
      <p class="mt-6">
        <router-link :to="{ name: 'login' }">Have an account?</router-link>
      </p>
    </v-container>
  </v-form>

  <!-- BELOW WAS STARTER CODE -->
  <!-- <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
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
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <router-link :to="{ name: 'login' }">Have an account?</router-link>
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create Account
      </button>
    </form>
  </div> -->
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
      loading: false,
      rulesUsername: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 20 || "Max 20 characters",
        (value) => (value || "").length > 2 || "Min 3 characters",
      ],
      rulesPassword: [
        value => !!value || 'Required',
        value => (value || '').length <= 80 || 'Max 80 characters',
        value => (value || '').length > 5 || 'Min 6 characters',
      ],
    };
  },
  methods: {
    register() {
      if (!this.$refs.form.validate()) return;
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>

<style>
#registerBox {
  max-width: 400px;
}
</style>
