import axiosClient from "@/api/axiosClient"

const paymentApi = {
  createPayment(billId: string, amount: number) {
    const data = {
      amount: amount,
      orderCode: billId,
    }
    const url = "pay/create_payment"
    return axiosClient.post(url, data)
  },
}

export default paymentApi
