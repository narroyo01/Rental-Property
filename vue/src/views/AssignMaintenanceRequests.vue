<template>
  <div>
    <div v-if="requests === null || technicians === null">
      <v-progress-linear indeterminate color="cyan"></v-progress-linear>
    </div>
    <div v-else>
      <h1 class="text-center">Maintenance Requests</h1>{{technicianObject}}
      <div
        style="display: inline-block; vertical-align: top"
        v-for="request in requests"
        :key="request.maintenanceRequestId"
      >
        <assign-maintenance-requests-card :data="{request:request,technicians:technicians}" @assignTechnician="assignTechnician" />
      </div>
    </div>
  </div>
</template>

<script>
import AssignMaintenanceRequestsCard from '../components/AssignMaintenanceRequestsCard.vue';
import maintenanceService from "../services/MaintenanceService";
import userService from "../services/UserService";
export default {
  components: { AssignMaintenanceRequestsCard },
  name: "AssignMaintenanceRequests",
  data() {
    return {
      requests: null,
      technicianObject: null,
      technicians: null,
      
    };
  },
  created() {
    this.getOpen();
    userService.getUsersByRole("ROLE_MAINTENANCE").then((response) => {
      if (response.status === 200) {
        this.techniciansObject = response.data;
        console.log(this.techniciansObject)
        let arr = [];
        this.techniciansObject.forEach((tech) =>{
          arr.push(tech.username)
        })
        this.technicians = arr
      }
    });
  },
  methods: {
    getOpen() {
      if (this.$store.state.user.id) {
        this.requests = null;
        maintenanceService.getOpenMaintenanceRequests().then((response) => {
          if (response.status === 200) {
            this.requests = response.data;
          }
        });
      } else {
        this.$router.push("/login");
      }
    },
    assignTechnician(data) {
      maintenanceService
        .assignTechnician(data.id, this.getTechnicianIdByUsername(data.technician))
        .then((response) => {
          if (response.status === 200) {
            this.getOpen();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getTechnicianIdByUsername(username){
      return this.techniciansObject.find((technician) =>{
        return technician.username === username;
      }).id;
    }
  },
};
</script>

<style>
</style>