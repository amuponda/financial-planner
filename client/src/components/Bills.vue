<template>
  <div class="container">
    <div class="row mt-5">
      <div class="col-sm-4">
        <!-- bills summary -->
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Summary</h5>
            <div>
              <small>
                <strong>Monthly Total:&nbsp;</strong>
              </small>
              <strong>$0.00</strong><br>
              <small>
                <strong>Month To Date:&nbsp;</strong>
              </small>
              <strong>$0.00</strong><br>
              <small>
                <strong>Month Remaining:&nbsp;</strong>
              </small>
              <strong>$0.00</strong>
            </div>
            <div class="mt-3">
              <button type="button" class="btn btn-primary btn-sm" @click="toggleModal">Add Bill</button>
            </div>
          </div>
        </div>
      </div>

      <!--Bills list -->
      <div class="col-sm-8">
        <div class="card">
          <div class="card-body">
            <div class="card-title">Bills</div>
            <table v-if="bills">
              <thead>
              <tr>
                <th>Name</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="bill in bills" :key="bill.id">
                <td v-text="bill.name"></td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <create-bill-modal :accounts="accounts" :show="showModal" @on-close="toggleModal"></create-bill-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import CreateBillModal from './CreateBillModal'
import modalMixin from '../mixins/ModalMixin'

export default {
  name: 'Bills',
  mixins: [modalMixin],
  computed: mapGetters({
    accounts: 'getAccounts',
    bills: 'getBills',
    isInitBills: 'isInitBills'
  }),
  components: { CreateBillModal },
  created () {
    if (!this.isInitBills) {
      this.$store.dispatch('fetchBills')
    }
  }
}
</script>

<style scoped>
</style>
