<template>
  <v-container id="form">
    <v-form @submit.prevent="submit" >
      <h1 id="header">Maintenance Request Form</h1>
      <v-text-field
        :loading="loading"
        :rules="rulesName"
        label="Name"
        v-model="request.name"
      ></v-text-field>
      <v-text-field
        :loading="loading"
        :rules="rulesPhone"
        label="Phone"
        v-model="request.phone"
      ></v-text-field>
      <v-text-field
        :loading="loading"
        :rules="rulesEmail"
        label="Email"
        v-model="request.email"
      ></v-text-field>
      <v-select
        :items="types"
        :rules="rulesSelect"
        label="Request Type"
        v-model="request.type"
        required
      ></v-select>
      <v-textarea
        :loading="loading"
        :rules="rulesDescription"
        label="Description"
        v-model="request.description"
      ></v-textarea>
      <v-btn class="mt-4" elevation="2" color="primary" type="submit"
        >Submit</v-btn
      >
    </v-form>
  </v-container>
</template>

<script>
export default {
  name: "RequestMaintenanceForm",
  components: {},
  data() {
    return {
      request: {
        name: "",
        email: "",
        phone: "",
        type: "",
        description: "",
      },
      invalidCredentials: false,
      loading: false,
      rulesName: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 30 || "Max 30 characters",
        (value) => (value || "").length > 2 || "Min 3 characters",
      ],
      rulesPhone: [
        (value) => !!value || "Required",
      ],
      rulesEmail: [
        (value) => !!value || "Required",
        (value) => this.validateEmail(value) || "Invalid Phone Number",
      ],
      rulesDescription: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 150 || "Max 150 characters",
        (value) => (value || "").length > 20 || "Min 20 characters",
      ],
      rulesSelect:[
        (value) => !!value || "Required"
      ],
      types: [
        "Plumbing",
        "Structural",
        "Electrical",
        "HVAC",
        "Exterior",
        "Other",
      ],
    };
  },
  methods: {
    phonenumber(inputtxt) {
      var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
      if (inputtxt.value.match(phoneno)) {
        return true;
      } else {
        alert("Invalid Number");
        return false;
      }
    },
    validateEmail(email) {
      return String(email)
        .toLowerCase()
        .match(
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        );
    },
    submit(){

    }
  },
};
</script>

<style scoped>
#form {
  max-width: 600px;
};

/* #header{
  text-align: center;
} */
</style>