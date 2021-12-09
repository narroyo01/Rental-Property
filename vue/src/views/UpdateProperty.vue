<template>
  <div>
    <div v-if="property.propertyId === ''">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <v-container id="updatePropertyBox">
        <h1>Update Property</h1>
        <v-form @submit.prevent="update" ref="form" class="mb-6">
          <v-text-field
            label="Address"
            :rules="rulesAddress"
            :loading="loading"
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
          ></v-text-field>

          <v-switch
            v-model="property.available"
            :label="property.available ? 'Available' : 'Not Available'"
          >
          </v-switch>

          <v-textarea
            label="Description"
            :rules="rulesDescription"
            v-model="property.description"
          ></v-textarea>
          <v-btn color="primary" type="submit" class="mt-4" elevation="2"
            >Update</v-btn
          >
          <v-alert class="mt-6" type="error" v-if="updatePropertyErrors">
            {{ updatePropertyErrorMsg }}
          </v-alert>
        </v-form>
        Image:
        <v-img
        
          :lazy-src="property.imageUrl"
          max-height="150"
          max-width="250"
          :src="property.imageUrl"
        ></v-img>
      </v-container>
    </div>
  </div>
</template>

<script>
import propertyService from "../services/PropertyService";
export default {
  name: "update-property",
  data() {
    return {
      updatePropertyErrors: false,
      updatePropertyErrorMsg: "There were problems updating the property",
      property: {
        propertyId: "",
        address: "",
        rent: 0,
        isAvailable: true,
        imageUrl: "",
      },
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
      loading: false,
    };
  },
  created() {
    propertyService.getById(this.$route.params.id).then((response) => {
      if (response.status === 200) {
        this.property = response.data;
      }
    });
  },
  methods: {
    update() {
      if (!this.$refs.form.validate()) {
        return;
      }

      this.loading = true;
      propertyService
        .update(this.property)
        .then((response) => {
          if (response.status === 200) {
            this.$router.push("/property/" + this.property.propertyId);
          }
        })
        .catch((error) => {
          this.loading = false;
          const response = error.response;
          this.updatePropertyErrors = true;
          if (response.status === 400) {
            this.updatePropertyErrorMsg = "Bad Request: Validation Errors";
          }
        });
    },
  },
};
</script>