<template>
  <div :class="['modal', 'fade', {'show': show}]" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Add Bill</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="closeModal">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="submit" novalidate>
            <div class="form-group row">
              <label for="account" class="col-sm-2">Account</label>
              <div class="col-sm-10">
                <select class="form-control" v-model="bill.account" name="account" id="account" required v-validate>
                  <option :value="null">Choose account</option>
                  <option v-for="account in accounts" :key="account.id" :value="account.id">{{account.name}}</option>
                </select>
                <p v-show="errors.has('account')" class="text-danger text-right small mt-1">{{ errors.first('account') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="name" class="col-sm-2">Name</label>
              <div class="col-sm-10">
                <input v-model="bill.name" name="name" id="name" required v-validate class="form-control" placeholder="Name" />
                <p v-show="errors.has('name')" class="text-danger text-right small mt-1">{{ errors.first('name') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="amount" class="col-sm-2">Amount</label>
              <div class="col-sm-10">
                <input v-model="bill.amount" class="form-control" name="amount" id="amount" required v-validate="'decimal:2'" placeholder="Amount" />
                <p v-show="errors.has('amount')" class="text-right text-danger small mt-1">{{ errors.first('amount') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="category" class="col-sm-2">Category</label>
              <div class="col-sm-10">
                <select class="form-control" v-model="bill.category" name="category" id="category" required v-validate>
                  <option :value="null">Choose category</option>
                  <option v-for="cat in categories" :key="cat.name" :value="cat.name">{{ cat.label }}</option>
                </select>
              </div>
            </div>
            <div class="form-group row">
              <label for="startDate" class="col-sm-2">Starts</label>
              <div class="col-sm-10">
                <input v-model="bill.startDate" type="date" name="startDate" id="startDate" required v-validate class="form-control" placeholder="Starts" />
                <p v-show="errors.has('startDate')" class="text-danger text-right small mt-1">{{ errors.first('startDate') }}</p>
              </div>
            </div>
            <div class="form-group row">
              <label for="repeats" class="col-sm-2">Repeats</label>
              <div class="col-sm-10">
                <select class="form-control" v-model="bill.repeats" name="repeats" id="repeats" required v-validate>
                  <option v-for="period in periodicity" :key="period.name" :value="period.name">{{ period.label }}</option>
                </select>
              </div>
            </div>
            <div class="form-group row">
              <label for="endDate" class="col-sm-2">Ends</label>
              <div class="col-sm-10">
                <input v-model="bill.endDate" type="date" name="endDate" id="endDate" v-validate class="form-control" placeholder="Ends">
              </div>
            </div>
            <button type="submit" class="btn btn-primary btn-sm">Add</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import dayjs from 'dayjs'

const initialData = {
  bill: {
    type: 'EXPENSE',
    account: null,
    name: 'Test Bill', // null,
    amount: 20, // null,
    category: 'ENTERTAINMENT',
    repeats: 'WEEKLY',
    startDate: new Date()
  }
}

export default {
  name: 'CreateBillModal',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    accounts: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      bill: Object.assign({}, initialData.bill),
      categories: [
        { name: 'ENTERTAINMENT', label: 'Entertainment' },
        { name: 'GROCERIES', label: 'Groceries' }
      ],
      periodicity: [
        { name: 'ONCE_OFF', label: 'Once Off' },
        { name: 'WEEKLY', label: 'Weekly' }
      ]
    }
  },
  methods: {
    closeModal () {
      this.bill = Object.assign({}, initialData.bill)
      this.errors.clear()
      this.$emit('on-close')
    },
    submit () {
      this.$validator.validateAll().then(result => {
        if (result) {
          this.addBill()
        } else {
          console.log(JSON.stringify(this.errors))
        }
      })
    },
    addBill () {
      let params = new URLSearchParams()
      params.append('account.id', this.bill.account)
      params.append('name', this.bill.name)
      params.append('category', this.bill.category)
      params.append('repeats', this.bill.repeats)
      params.append('startDate', dayjs().format('DD/MM/YYYY'))
      params.append('amount', this.bill.amount)
      params.append('type', 'EXPENSE')

      this.$store.dispatch('addBill', params).then(result => {
        console.log('done')
      })
        .catch(reason => {
          console.log('error')
        })
    }
  }
}
</script>

<style scoped>
  .modal-dialog {
    margin-top: 4rem;
  }
  .modal.show {
    display: block;
    background-color: rgba(0, 0, 0, .5);
  }
</style>
