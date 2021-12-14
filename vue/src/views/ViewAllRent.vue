<template>
  <v-simple-table v-if="isAuthorized" style="background-color:#eeeeee">
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
        <td v-bind:class="{green: transaction.status == 'paid'}" v-if=" transaction.status == 'paid'">{{transaction.status}}</td>
        <td v-bind:class="{yellow: transaction.status == 'due'}" v-if="transaction.status == 'due'">{{transaction.status}}</td>
        <td v-bind:class="{red: transaction.status == 'overdue'}" v-if="transaction.status == 'overdue'">{{transaction.status}}</td>
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
      color: null,
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
  },
};
</script>

<style scoped>
.green{
  background-color: green;
}

.yellow{
  background-color: yellow;
}

.red{
  background-color: red;
}
</style>