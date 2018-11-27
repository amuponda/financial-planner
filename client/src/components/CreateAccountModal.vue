<template>
  <div :class="['modal', 'fade', {'show': show}]" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Add Account</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="closeModal">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="addAccount" novalidate>
            <div class="form-group">
              <input v-model="newAccountName" name="name" v-validate="'required|alreadyExists|max:50'" class="form-control" placeholder="Account name">
              <p v-show="errors.has('name')" class="text-danger text-right small mt-1">{{ errors.first('name') }}</p>
            </div>
            <button type="submit" class="btn btn-primary btn-sm">Add</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CreateAccount',
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
      newAccountName: null
    }
  },
  computed: {
    accountNames () {
      return this.accounts.map(account => account.name)
    }
  },
  methods: {
    closeModal () {
      this.newAccountName = null
      this.errors.clear()
      this.$emit('on-close')
    },
    addAccount () {
      this.$validator.validateAll().then(result => {
        if (result) {
          this.$store.dispatch('addAccount', this.newAccountName)
        }
      })
    }
  },
  created () {
    this.$validator.extend('alreadyExists', {
      getMessage: () => 'You already have an account with that name.',
      validate: (value) => {
        return !this.accountNames.includes(value)
      }
    })
  }
}
</script>

<style scoped>
  .modal-dialog {
    margin-top: 8rem;
  }
  .modal.show {
    display: block;
    background-color: rgba(0, 0, 0, .5);
  }
</style>
