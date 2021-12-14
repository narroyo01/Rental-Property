<template>
  <div>
    <div v-if="properties === null">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <h1 class="text-center">Welcome to Waystone Property Management</h1>
      <h2 class="text-center">Featured Available Properties:</h2>
      <property-card
        v-for="property in properties"
        :key="property.id"
        :property="property"
        style="display: inline-block; vertical-align: top;"
        class="mx-6 my-12"
      />
    </div>
  </div>
</template>

<script>
import propertyService from "../services/PropertyService";
import PropertyCard from "../components/PropertyCard.vue";

export default {
  name: "Home",
  components: {
    PropertyCard,
  },
  data() {
    return {
      properties: null,
    };
  },
  created() {
    propertyService.getAvailableProperties().then((response) => {
      if (response.status === 200) {
        this.properties = response.data.splice(0,6);
      }
    });
  },
};
</script>
