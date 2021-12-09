<template>
  <v-container>
    <v-tabs v-model="tab">
      <v-tab>Detail View</v-tab>
      <v-tab>List View</v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item>
        <property-card-actions
          v-for="property in properties"
          :key="property.propertyId"
          :property="property"
          style="display: inline-block; vertical-align: top"
          class="mx-6 my-12"
        />
      </v-tab-item>
      <v-tab-item>
        <v-simple-table>
          <thead>
            <tr>
              <th class="text-left">Address</th>
              <th class="text-left">Rent</th>
              <th class="text-left" v-if="isAuthorized">Availability</th>
              <th class="text-left" v-if="isAuthorized">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="property in properties" :key="property.propertyId">
              <td>{{ property.address }}</td>
              <td>${{ property.rent }}</td>
              <td class="text-left" v-if="isAuthorized">
                {{ property.available ? "Available" : "Occupied" }}
              </td>
              <td v-if="isAuthorized">
                <v-btn
                  @click="navToUpdate(property.propertyId)"
                  class="me-4"
                  v-if="isAuthorized"
                  >Update</v-btn
                >
                <v-btn v-if="isAuthorized">Create Maintenance Request</v-btn>
              </td>
            </tr>
          </tbody>
        </v-simple-table>
      </v-tab-item>
    </v-tabs-items>
  </v-container>
</template>

<script>
import PropertyCardActions from "../components/PropertyCardActions.vue";
import propertyService from "../services/PropertyService";

export default {
  components: { PropertyCardActions },
  name: "ViewAllProperties",
  data() {
    return {
      tab: 0,
      properties: null,
    };
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
    propertyService.getProperties().then((response) => {
      if (response.status === 200) {
        this.properties = response.data;
      }
    });
  },
  methods: {
    navToUpdate(id) {
      this.$router.push("/update-property/" + id);
    },
  },
};
</script>

<style scoped>
/* #list {
  max-width: 600px;
} */
</style>