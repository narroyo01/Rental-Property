<template>
  <v-simple-table v-if="isAuthorized">
    <thead>
      <tr>
        <th>Property</th>
        <th>Address</th>
        <th>Date Due</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="transaction in transactions" :key="transaction.transactionId">
        <td>{{transaction.propertyId}}</td>
        <td>{{transaction.address}}</td>
        <td>{{transaction.timeDue}}</td>
        <td>{{transaction.status}}</td>
      </tr>
    </tbody>
  </v-simple-table>
  <h1 v-else>You are not authorized to view this information</h1>
</template>

<script>
import transactionService from "../services/TransactionService";
export default {
  name: "ViewAllRent",
  data() {
    return {
      transactions: null,
    };
  },
  created() {
    transactionService.getAll().then((response) => {
      if (response.status === 200) {
        this.transactions = response.data;
      }
    });
  },
  computed: {
    isAuthorized() {
      return (
        this.$store.state.user.authorities &&
        (this.$store.state.user.authorities[0].name === "ROLE_LANDLORD" ||
          this.$store.state.user.authorities[0].name === "ROLE_ADMIN")
      );
    },
  }
};
</script>