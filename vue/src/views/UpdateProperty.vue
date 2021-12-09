<template>
  <div>
    <div v-if="property.propertyId === ''">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <v-container id="updatePropertyBox">
        <h1>Update Property</h1>
      </v-container>
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
    </div>
  </div>
</template>

<script>
import propertyService from "../services/PropertyService";
export default {
  name: "update property",
  data() {
    return {
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
    };
  },
  created() {
    propertyService.getById(this.$route.params.id).then((response) => {
      if (response.status === 200) {
        this.property = response.data;
      }
    });
  },
};
</script>