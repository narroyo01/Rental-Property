<template>
  <v-container>
    <h1 class="text-center">Renter Dashboard</h1>
    <!-- <h2 v-if="ledgers.length && ledgers.length < 1">You have no available transactions.</h2> -->
    <v-tabs background-color="grey lighten-3" v-model="tab">
      <v-tab v-for="ledger in ledgers" :key="ledger.id">
        {{ ledger.address }}
      </v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item
        style="background-color: #eeeeee"
        v-for="ledger in ledgers"
        :key="ledger.id"
      >
        <v-container>
          <h2 class="mt-8">Ledger</h2>
          <v-simple-table
            class="mb-8"
            style="background-color: #eeeeee"
            v-if="isAuthorized"
          >
            <thead>
              <tr>
                <th>Date Posted</th>
                <th>Date Due</th>
                <th>Rent</th>
                <th>Amount Paid</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="transaction in ledger.ledger" :key="transaction.id">
                <td>
                  {{ parseDate(transaction.timeInitiated) }}
                </td>
                <td>
                  {{ transaction.timeDue }}
                </td>
                <td>${{ transaction.amountDue }}</td>
                <td>${{ transaction.amountPaid }}</td>
                <td>
                  <v-btn
                    color="primary"
                    v-if="transaction.amountPaid < transaction.amountDue"
                    @click="payRent(transaction)"
                  >
                    PAY
                  </v-btn>
                </td>
              </tr>
            </tbody>
          </v-simple-table>
          <request-maintenance-form :propertyId="ledger.ledger[0].propertyId" />
        </v-container>
      </v-tab-item>
    </v-tabs-items>

    <v-dialog v-if="selectedTransaction" v-model="dialog" width="500">
      <v-card>
        <v-card-title class="text-h5 grey lighten-2"> Pay Rent </v-card-title>

        <v-card-text class="mt-6">
          <h3>Rent Amount:</h3>
          ${{ selectedTransaction.amountDue }}
          <h3>Amount Paid:</h3>
          ${{ selectedTransaction.amountPaid }}
          <h3>Amount Due:</h3>
          ${{ selectedTransaction.amountDue - selectedTransaction.amountPaid }}
          <v-text-field
            label="Payment Amount"
            prefix="$"
            outlined
            class="mt-6"
            v-model="paymentAmount"
            type="number"
          >
          </v-text-field>
          <v-alert class="mt-6" type="success" v-if="success">
            Payment Successful!
          </v-alert>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="danger" text @click="dialog = false"> Cancel </v-btn>
          <v-btn color="primary" text @click="pay()"> Submit Payment </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import RequestMaintenanceForm from "../components/RequestMaintenanceForm.vue";
import transactionService from "../services/TransactionService";
import moment from "moment";

export default {
  components: { RequestMaintenanceForm },
  name: "ViewMyRent",
  data() {
    return {
      tab: null,
      ledgers: null,
      color: null,
      dialog: false,
      selectedTransaction: null,
      paymentAmount: 0,
      success: false,
    };
  },
  //
  created() {
    if (!this.$store.state.user.id) {
      return;
    }
    this.get();
  },
  computed: {
    isAuthorized() {
      return this.$store.state.user.authorities;
    },
  },
  methods: {
    parseDate(string) {
      return moment(string).format("YYYY-MM-DD");
    },

    payRent(transaction) {
      this.dialog = true;
      this.paymentAmount = transaction.amountDue - transaction.amountPaid;
      this.selectedTransaction = transaction;
    },
    pay() {
      transactionService
        .payRent(this.selectedTransaction.transactionId, this.paymentAmount)
        .then((response) => {
          if (response.status === 200) {
            this.success = true;
            this.get();
          }
        });
    },
    get() {
      transactionService
        .getMyRent(this.$store.state.user.id)
        .then((response) => {
          if (response.status === 200) {
            this.ledgers = response.data;
          }
        });
    },
  },
};
</script>