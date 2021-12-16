<template>
  <div>
    <div v-if="properties === null">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <div
        style="
          background-color: #004d40;
          height: 245px;
          width: 500px;
          margin: auto;
          border-radius: 20px;
          filter: drop-shadow(6px 6px 6px black);
        "
      >
        <div style="display: flex; justify-content: center; position:relative; left:-10px;">
          <v-img
            class="mt-3 ms-3"
            max-height="300"
            max-width="300"
            src="../assets/wpm.png"
            background-color="#004D40"
            style=""
          ></v-img>
        </div>
      </div>
      <h2 class="text-center mt-12">Featured Available Properties:</h2>
      <div style="display: flex; flex-wrap: wrap; justify-content: center">
      <property-card
        v-for="property in properties"
        :key="property.id"
        :property="property"
        style=" vertical-align: top"
        class="mx-6 my-6"
      />
    </div>
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
        this.properties = response.data.splice(0, 6);
      }
    });
  },
};
</script>

<style scoped>
</style>