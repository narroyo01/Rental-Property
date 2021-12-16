<template>
  <div>
    <v-form ref="form" @submit.prevent="submitContactForm">
      <h3>Contact Us About This Property:</h3>

      <v-text-field
        label="Name"
        :loading="loading"
        :rules="rulesName"
        v-model="contactForm.name"
      ></v-text-field>

      <v-text-field
        label="Email Address"
        :loading="loading"
        :rules="rulesEmail"
        v-model="contactForm.email"
      ></v-text-field>

      <v-text-field
        label="Phone Number"
        :loading="loading"
        :rules="rulesPhone"
        v-model="contactForm.phone"
      ></v-text-field>

      <v-textarea
        label="Message"
        :loading="loading"
        :rules="rulesMessage"
        v-model="contactForm.message"
      ></v-textarea>
      <v-alert class="mt-6" type="success" v-if="success">
        Message sent! We'll be in touch soon.
      </v-alert>
      <v-btn class="my-4" elevation="2" color="primary" type="submit"
        >Submit</v-btn
      >
    </v-form>
  </div>
</template>

<script>
import inquiryService from "../services/InquiryService";

export default {
  name: "ContactForm",
  data() {
    return {
      loading: false,
      success: false,
      rulesName: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 50 || "Max 50 characters",
      ],
      rulesEmail: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 100 || "Max 100 characters",
      ],
      rulesPhone: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 20 || "Max 20 characters",
      ],
      rulesMessage: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 300 || "Max 300 characters",
      ],

      contactForm: {
        name: "",
        email: "",
        phone: "",
        message: "",
        propertyId: this.$route.params.id,
      },
    };
  },
  methods: {
    submitContactForm() {
      if (!this.$refs.form.validate()) return;
      this.loading = true;
      this.success = false;
      inquiryService
        .submit(this.contactForm)
        .then((response) => {
          if (response.status == 201) {
            this.success = true;
            this.loading = false;
            this.$refs.form.reset();
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
  },
};
</script>

<style scoped>
</style>