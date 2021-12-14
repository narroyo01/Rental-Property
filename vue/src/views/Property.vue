<template>
  <div>
    <div v-if="property === null">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <v-container id="propertyBox">
        <h1>{{ property.address }}</h1>
        <v-img
          :src="property.imageUrl"
          max-width="800"
          style="border-radius: 10px"
        ></v-img>
        <p class="mt-4" v-if="isAuthorized">
          {{ property.available ? "Available" : "Occupied" }}
        </p>

        <p class="mt-4">{{ property.description }}</p>

        <p class="mt-4">Rent: ${{ property.rent }}</p>

        <v-btn
          class="ma-2"
          v-if="isAuthorized"
          @click="navToUpdate(property.propertyId)"
          >Update</v-btn
        >
        <v-btn class="ma-2" v-if="isAuthorized"
          >Create Maintenance Request</v-btn
        >
        <request-maintenance-form :propertyId ="this.$route.params.id" v-if="isAuthorized"/>
        <inquiry-form/>
      </v-container>
    </div>
  </div>
</template>

<script>
import propertyService from "../services/PropertyService";
import RequestMaintenanceForm from "../components/RequestMaintenanceForm.vue";

export default {
  name: "Property",
  components: {
    RequestMaintenanceForm,
  },
  data() {
    return {
      property: null,
    };
  },
  methods: {
    navToUpdate(id) {
      this.$router.push("/update-property/" + id);
    },
  },
  computed: {
    isAuthorized() {
      return (
        this.$store.state.user.authorities &&
        (this.$store.state.user.authorities[0].name === "ROLE_LANDLORD" ||
          this.$store.state.user.authorities[0].name === "ROLE_ADMIN")
      );
    },
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

<style scoped>
#propertyBox {
  max-width: 800px;
}
</style>