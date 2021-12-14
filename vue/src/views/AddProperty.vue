<template>
  <v-form ref="form" @submit.prevent="addProperty">
    <v-container id="addPropertyBox" style="background-color:white">
      <h1 class="h3 mb-3 font-weight-normal">Add Property</h1>

      <v-text-field
        label="Unit Address"
        :loading="loading"
        :rules="rulesAddress"
        v-model="property.address"
      ></v-text-field>

      <v-text-field
        label="Rent"
        :loading="loading"
        :rules="rulesRent"
        type="number"
        step="0.01"
        prefix="$"
        v-model="property.rent"
      >
      </v-text-field>

      <v-text-field
        label="Image URL"
        :loading="loading"
        :rules="rulesImage"
        v-model="property.imageUrl"
      >
      </v-text-field>

      <v-textarea
        label="Description"
        :rules="rulesDescription"
        v-model="property.description"
      ></v-textarea>

      <v-btn class="my-4" elevation="2" color="primary" type="submit"
        >Add Property</v-btn
      >
      <v-alert class="my-6" type="error" v-if="addPropertyErrors">
        {{ addPropertyErrorMsg }}
      </v-alert>
      <div>Image:</div>
      <v-img
        :lazy-src="property.imageUrl"
        max-width="600"
        :src="property.imageUrl"
      ></v-img>
    </v-container>
  </v-form>
</template>

<script>
import propertyService from "../services/PropertyService";

export default {
  name: "addProperty",
  data() {
    return {
      property: {
        address: "",
        rent: 0.0,
        imageUrl: "",
        description: "",
      },
      addPropertyErrors: false,
      addPropertyErrorMsg: "There were problems adding the property.",
      loading: false,
      rulesImage: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 500 || "Max 500 characters",
        (value) => (value || "").length > 2 || "Min 3 characters",
      ],
      rulesRent: [(value) => !!value || "Required"],
      rulesAddress: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 200 || "Max 200 characters",
        (value) => (value || "").length > 2 || "Min 3 characters",
      ],
      rulesDescription: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 1000 || "Max 1000 characters",
      ],
    };
  },
  methods: {
    addProperty() {
      this.clearErrors();
      if (!this.$refs.form.validate()) return;
      this.loading = true;
      propertyService
        .add(this.property)
        .then((response) => {
          if (response.status == 200) {
            this.$router.push("/property/" + response.data);
          }
        })
        .catch((error) => {
          this.loading = false;
          const response = error.response;
          this.addPropertyErrors = true;
          if (response.status === 400) {
            this.addPropertyErrorMsg = "Bad Request: Validation Errors";
          }
        });
    },
    clearErrors() {
      this.addPropertyErrors = false;
      this.addPropertyErrorMsg = "There were problems adding the property.";
    },
  },
};
</script>

<style scoped>
#addPropertyBox {
  max-width: 600px;
}
</style>