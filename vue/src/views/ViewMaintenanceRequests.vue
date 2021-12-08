<template>
  <div>
    <div v-if="requests === null">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <div
        style="display: inline-block"
        v-for="request in requests"
        :key="request.id"
      >
        <v-card class="ma-6" max width="344" elevation="2">
          <v-card-text>
            <h2>{{ request.address }}</h2>
            <p>Requested: {{ request.timestamp }}</p>
            <p>Request Type: {{ request.description }}</p>
            <p>Contact Info: {{ request.contactInfo }}</p>
          </v-card-text>
          <v-card-actions>
            <v-btn @click="markComplete">Mark As Complete</v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import maintenanceService from "../services/MaintenanceService";
export default {
  name: "ViewMaintenanceRequests",
  data() {
    return {
      requests: null,
      //  [
      //   {
      //     id: 1,
      //     address: "123 Main St.",
      //     description: "HVAC",
      //     contactInfo: "555-5555",
      //     timestamp: Date.now(),
      //   },
      //   {
      //     id: 2,
      //     address: "450 Poplar Ln.",
      //     description: "Plumbing",
      //     contactInfo: "555-7777",
      //     timestamp: Date.now(),
      //   },
      // ],
    };
  },
  created() {
    if (this.$store.state.user.id) {
      maintenanceService
        .getByUserId(this.$store.state.user.id)
        .then((response) => {
          if(response.status === 200){
             this.requests = response.data;
          }
        });
    } else {
      this.$router.push("/login");
    }
  },
  methods:{
    markComplete(){

    }
  }
};
</script>

<style scoped>
</style>