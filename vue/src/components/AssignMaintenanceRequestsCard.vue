<template>
 <v-card class="ma-6" max width="344" elevation="2">
    <v-card-text>
      <h2 class="mb-4">{{ data.request.address }}</h2>
      <p>Requested: {{ parseDate(data.request.timeStamp) }}</p>
      <p>Request Type: {{ data.request.description }}</p>
      <p>Comments: {{data.request.comments}}</p>
      <p>Contact Info: <ul><li>{{ data.request.name }}</li><li>{{ data.request.phone }}</li><li>{{ data.request.email }}</li></ul></p>
    </v-card-text>
    <v-card-actions style= "flex-direction: column; align-items:stretch;" v-if="data.technicians">
      <div style="">
        <v-autocomplete :items="data.technicians" label="Select Technician" outlined v-model="technician" ></v-autocomplete>
      </div>
      <v-btn color="primary" class="mx-auto mb-4" @click="$emit('assignTechnician',{id:data.request.maintenanceRequestId,technician:technician})">Assign Technician</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import moment from 'moment'

export default {
  props: ["data"],
  data() {
    return {
      technician: null,
    };
  },
  methods: {
    parseDate(string) {
      return moment(string).format("DD MMM YYYY LT");
    },
  },
};
</script>

<style>
</style>