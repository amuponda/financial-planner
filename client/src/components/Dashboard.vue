<template>
  <div class="container">
    <div class="row mt-5">
      <div class="col-sm-4">
        <!-- account summary -->
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Summary</h5>
            <div>
              <small>
                <strong>This Month</strong><br>
              </small>
              <small>
                <strong>Spent:&nbsp;</strong>
              </small>
              <strong>$0.00</strong><br>
              <small>
                <strong>Earned:&nbsp;</strong>
              </small>
              <strong>$0.00</strong>
            </div>
            <div>
              <small>
                <strong>Total Balance:</strong>
              </small>
              <strong class="text-success">$0.00</strong>
            </div>
          </div>
        </div>

        <!-- account list -->
        <div class="card mt-3">
          <div class="card-body">
            <h5 class="card-title">Accounts</h5>
            <table v-if="accounts.length > 0" class="table table-sm mt-3">
              <tbody>
                <tr v-for="account in accounts" :key="account.name">
                  <td>
                    <small>
                      <strong>{{ account.name }}</strong>
                    </small>
                  </td>
                  <td class="text-right font-weight-bold">{{ account.balance | currency }}</td>
                </tr>
              </tbody>
            </table>
            <div v-else>
              <p>
                <strong>No bank account setup</strong><br>
                <span>Get started by adding a bank account</span>
              </p>
            </div>
            <button type="button" class="btn btn-primary btn-sm" @click="toggleModal">Add Account</button>
          </div>
        </div>
      </div>

      <div class="col-sm-8">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Transactions</h5>
          </div>
        </div>
      </div>
    </div>

    <create-account-modal @on-close="toggleModal" :show="showModal" :accounts="accounts"></create-account-modal>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CreateAccountModal from './CreateAccountModal'

export default {
  name: 'Dashboard',
  data () {
    return {
      showModal: false
    }
  },
  computed: mapGetters({
    accounts: 'getAccounts'
  }),
  components: { CreateAccountModal },
  methods: {
    toggleModal () {
      this.showModal = !this.showModal
    }
  }
}
</script>

<style scoped>
</style>
