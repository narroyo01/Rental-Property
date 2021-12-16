<template >
  <div class="mx-4">
    <v-tabs v-model="tab" background-color="grey lighten-3">
      <v-tab>Detail View</v-tab>
      <v-tab>List View</v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item style="background-color: #eeeeee">
        <div style="display: flex; flex-wrap: wrap; justify-content: center">
          <property-card-actions
            v-for="property in properties"
            :key="property.propertyId"
            :property="property"
            style="display: inline-block; vertical-align: top"
            class="mx-6 my-6"
          />
        </div>
      </v-tab-item>
      <v-tab-item>
        <v-simple-table style="background-color: #eeeeee">
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
              <td>
                <router-link :to="'/property/' + property.propertyId">
                  {{ property.address }}
                </router-link>
              </td>
              <td>${{ property.rent }}</td>
              <td class="text-left" v-if="isAuthorized">
                {{ property.available ? "Available" : "Occupied" }}
              </td>
              <td v-if="isAuthorized">
                <v-btn
                  color="secondary"
                  @click="navToUpdate(property.propertyId)"
                  class="me-4"
                  v-if="isAuthorized"
                  >Update</v-btn
                >
              </td>
            </tr>
          </tbody>
        </v-simple-table>
      </v-tab-item>
    </v-tabs-items>
  </div>
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
#bg {
  background-color: #eeeeee;
}
</style>