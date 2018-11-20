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
            <button type="button" class="btn btn-primary btn-sm" @click="showModal">Add Account</button>
            <table class="table table-sm mt-3">
              <tbody>
                <tr v-for="account in accounts" :key="account.name">
                  <td>{{ account.name }}</td>
                  <td class="text-right font-weight-bold">{{ account.balance | currency }}</td>
                </tr>
              </tbody>
            </table>
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

    <!-- add account modal -->
    <div :class="['modal', 'fade', {'show': isModal}]" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Add Account</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="showModal">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="addAccount" novalidate>
              <div class="form-group">
                <input v-model="newAccountName" name="account" v-validate="'required'" class="form-control" placeholder="Account name">
                <p v-show="errors.has('account')" class="text-danger">{{ errors.first('account') }}</p>
              </div>
              <button type="submit" class="btn btn-primary btn-sm">Add</button>
            </form>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'dashboard',
  beforeRouteEnter (to, from, next) {
    next(vm => {
      vm.$store.dispatch('fetchAccounts')
    })
  },
  data () {
    return {
      isModal: false,
      newAccountName: null
    }
  },
  computed: mapGetters({
    accounts: 'getAccounts'
  }),
  methods: {
    showModal () {
      this.isModal = !this.isModal
    },
    addAccount () {
      this.$store.dispatch('addAccount', this.newAccountName)
    }
  }
}
</script>

<style scoped>
  .modal.show {
    display: block;
    background-color: rgba(0, 0, 0, .5);
  }
</style>
