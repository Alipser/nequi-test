resource "aws_dynamodb_table" "nequi_table" {
  name           = var.table_name
  billing_mode   = "PAYPER_REQUEST"
  hash_key       = "franchise"
  range_key      = "compositeKey"

  attribute {
    name = "franchise"
    type = "S"
  }

  attribute {
    name = "compositeKey"
    type = "S"
  }

  attribute {
    name = "stock"
    type = "N"
  }

  global_secondary_index {
    name            = "ix-franchise-stock"
    hash_key        = "franchise"
    range_key       = "stock"
    projection_type = "ALL"
  }
}
