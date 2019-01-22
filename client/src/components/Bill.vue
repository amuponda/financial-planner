<template>
  <div class="container">
    <div class="row mt-5">
      <!-- bill summary -->
      <div class="col-md-4">
        <div class="card">
          <div class="card-body">
            <h5>Bill Summary</h5>
            <div>
              <strong>Amount:&nbsp;</strong>
              <strong>$0.00&nbsp;</strong>
              <strong><button class="btn btn-primary btn-sm">Edit</button></strong><br>

              <strong>Repeats:&nbsp;</strong>
              <strong>WEEKLY</strong><br>

              <strong>Next Due:&nbsp;</strong>
              <strong>12/01/2020</strong><br>
            </div>
            <div>
              <strong>This FY:&nbsp;</strong>
              <strong>$0.00</strong><br>

              <strong>Grand Total:&nbsp;</strong>
              <strong>$0.00</strong>
            </div>
          </div>
        </div>
      </div>

      <!-- graph + transactions -->
      <div class="col-md-8">
        <div></div>
        <div class="card">
          <div class="card-body table-responsive">
            <h5 class="card-title">Recent Transactions</h5>
            <table class="table table-striped table-sm">
              <thead>
              <tr>
                <th>Date</th>
                <th>Name</th>
                <th>Category</th>
                <th>Amount</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Bill'
}
</script>

<style scoped>

</style>
