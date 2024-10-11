export interface User {
  role: Role[]
  token: string
  id:number
  sdt: string
  accountName: string
  imgUser: string
  username: string
  email?:string | null
}

export interface Role {
  id: number
  createDate: any
  status: any
  authority: string
}
