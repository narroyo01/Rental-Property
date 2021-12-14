<template>
 <v-card class="ma-6 blue-grey lighten-5" max width="344" elevation="2">
    <v-card-text>
      <h2 class="mb-4">{{ data.request.address }}</h2>
      <p>Requested: {{ data.request.timeStamp }}</p>
      <p>Request Type: {{ data.request.description }}</p>
      <p>Comments: {{data.request.comments}}</p>
      <p>Contact Info: <ul><li>{{ data.request.name }}</li><li>{{ data.request.phone }}</li><li>{{ data.request.email }}</li></ul></p>
    </v-card-text>
    <v-card-actions style= "flex-direction: column" v-if="data.technicians">
      <div style="flex: 1 0 100%" >
       <v-select   :items="data.technicians" label="Select Technician"  outlined v-model="technician" ></v-select>
      </div>
      <div>
        <v-btn class="mx-auto mb-4 green lighten-2" @click="$emit('assignTechnician',{id:data.request.maintenanceRequestId,technician:technician})">Assign Technician</v-btn>
      </div>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  props: ["data"],
  data() {
    return {
      technician: null,
    };
  },
};
</script>

<style>
</style>