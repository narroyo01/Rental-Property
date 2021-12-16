<template>
  <v-container id="form">
    <v-form
      v-if="maintenanceTypes !== null"
      ref="form"
      @submit.prevent="submit"
    >
      <h2 id="header">Maintenance Request Form</h2>
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
        v-model="type"
        required
      ></v-select>
      <v-textarea
        :loading="loading"
        :rules="rulesDescription"
        label="Description"
        v-model="request.comments"
      ></v-textarea>
      <v-alert class="my-4" type="success" v-if="success">
        Maintenance Request submitted successfully, a technician will be in
        touch as soon as possible.
      </v-alert>
      <v-btn class="mt-4" elevation="2" color="primary" type="submit"
        >Submit</v-btn
      >
    </v-form>
  </v-container>
</template>

<script>
import MaintenanceService from "../services/MaintenanceService";
export default {
  name: "RequestMaintenanceForm",
  components: {},
  props: ["propertyId"],
  data() {
    return {
      success: false,
      maintenanceTypes: null,
      types: null,
      type: "",
      request: {
        typeId: "",
        timeStamp: Date.now(),
        requesterId: "",
        name: "",
        email: "",
        phone: "",

        comments: "",
        propertyId: this.propertyId,
      },
      maintenanceRequestErrors: false,
      maintenanceRequestErrorsMsg:
        "There were problems submitting maintenance request.",
      invalidCredentials: false,
      loading: false,
      rulesName: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 30 || "Max 30 characters",
        (value) => (value || "").length > 2 || "Min 3 characters",
      ],
      rulesPhone: [(value) => !!value || "Required"],
      rulesEmail: [(value) => !!value || "Required"],
      rulesDescription: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 1000 || "Max 1000 characters",
        (value) => (value || "").length > 20 || "Min 20 characters",
      ],
      rulesSelect: [(value) => !!value || "Required"],
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
    submit() {
      this.clearErrors();
      this.success = false;
      if (!this.$refs.form.validate()) return;
      this.loading = true;
      this.request.requesterId = this.$store.state.user.id;
      this.request.typeId = this.getSelectedTypeId();
      MaintenanceService.maintenanceRequest(this.request)
        .then((response) => {
          if (response.status === 201) {
            this.success = true;
            this.loading = false;
          }
        })
        .catch((error) => {
          this.loading = false;
          const response = error.response;
          this.maintenanceRequestErrors = true;
          if (response.status === 400) {
            this.maintenanceRequestErrors = "Invalid Maintenance Request";
          }
        });
    },
    getSelectedTypeId() {
      return this.maintenanceTypes.find((type) => {
        return type.description === this.type;
      }).maintenanceTypeId;
    },
    clearErrors() {
      this.maintenanceRequestErrors = false;
      this.maintenanceRequestErrorsMsg =
        "There were problems submitting maintenance request.";
    },
  },
  created() {
    /* eslint-disable no-unused-vars */
    MaintenanceService.getTypes().then((response) => {
      if (response.status === 200) {
        this.maintenanceTypes = response.data;
        let arr = [];
        for (const [key, value] of Object.entries(this.maintenanceTypes)) {
          arr.push(value.description);
        }
        this.types = arr;
      }
    });
  } /* eslint-disable no-unused-vars */,
};
</script>

<style scoped>
#form {
  max-width: 600px;
}

/* #header{
  text-align: center;
} */
</style>