<template>
  <div>
    <div v-if="requests === null">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <h1 class="text-center">Maintenance Requests</h1>
      <h2 class="text-center">Assigned to Me</h2>
      <div
        style="display: inline-block"
        v-for="request in requests"
        :key="request.maintenanceRequestId"
      >
        <v-card class="ma-6" max width="344" elevation="2">
          <v-card-text>
            <h2 class="mb-4">{{ request.address }}</h2>
            <p>Requested: {{ parseDate(request.timeStamp) }}</p>
            <p>Request Type: {{ request.description }}</p>
            <p>Comments: {{request.comments}}</p>
            <p>Contact Info: <ul><li>{{ request.name }}</li><li>{{ request.phone }}</li><li>{{ request.email }}</li></ul></p>
          </v-card-text>
          <v-card-actions>
            <v-btn class="mx-auto mb-4 secondary" @click="markComplete(request.maintenanceRequestId)">Mark As Complete</v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import maintenanceService from "../services/MaintenanceService";
// import moment from 'moment';
import moment from "moment";

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
    this.get();
  },
  computed: {
    date() {
      return moment(this.requests.timeStamp, "MM-DD-YYYY");
    },
  },
  methods: {
    parseDate(string) {
      return moment(string).format("DD MMM YYYY LT");
    },
    get() {
      if (this.$store.state.user.id) {
        maintenanceService
          .getByUserId(this.$store.state.user.id)
          .then((response) => {
            if (response.status === 200) {
              this.requests = response.data;
            }
          });
      } else {
        this.$router.push("/login");
      }
    },
    markComplete(maintenanceRequestId) {
      maintenanceService
        .markAsCompleteByMaintenanceRequestId(maintenanceRequestId)
        .then((response) => {
          if (response.status === 200) {
            this.get();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
</style>