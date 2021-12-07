<template>
  <v-form ref="form" @submit.prevent="addProperty">
    <v-container id="addPropertyBox">
      <h1 class="h3 mb-3 font-weight-normal">Add Property</h1>
      <v-text-field
        :loading="loading"
        :rules="rulesAddress"
        label="Unit Address"
        v-model="property.address"
      ></v-text-field>
      <v-text-field
        label="Rent"
        type="number"
        step="0.01"
        prefix="$"
        v-model="property.rent"
      >
      </v-text-field>
      <v-file-input
        accept="image/*"
        prepend-icon="mdi-camera"
        show-size
        label="Upload image"
        :rules="rulesImage"
        v-model="property.img"
      ></v-file-input>
      <v-btn class="mt-4" elevation="2" color="primary" type="submit"
        >Add Property</v-btn
      >
      <v-alert class="mt-6" type="error" v-if="addPropertyErrors">
        {{addPropertyErrorMsg}}
      </v-alert>
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
        img: null,
      },
      addPropertyErrors: false,
      addPropertyErrorMsg: "There were problems adding the property.",
      loading: false,
      rulesImage: [
        (value) =>
          !value ||
          value.size < 2000000 ||
          "Image size should be less than 2 MB!",
      ],
      rulesAddress: [
        (value) => !!value || "Required",
        (value) => (value || "").length <= 20 || "Max 120 characters",
        (value) => (value || "").length > 2 || "Min 3 characters",
      ],
    };
  },
  methods: {
    addProperty() {
      console.log(this.property.img)
      this.clearErrors();
      if (!this.$refs.form.validate()) return;
      this.loading = true;
      propertyService.add(this.property).then((response) => {
        if (response.status == 200) {
          //TODO this.$router.push("/property/" + response.data.id)
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
    }
  },
};
</script>

<style scoped>
#addPropertyBox {
  max-width: 600px;
}
</style>