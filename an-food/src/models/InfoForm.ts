import { Maybe } from "yup"

export interface InfoForm {
  sdt: string
  accountName: string
  imgUser?: string
  username: string
  email?: Maybe<string | undefined>
}
