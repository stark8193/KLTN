function getList(labelList: string[], navList: string[]) {
  const list = labelList.map((item, index) => ({
    label: item,
    nav: "/admin" + navList[index],
  }))
  return list
}

export const InvoiceList = getList(
  ["Đơn hàng"],
  ["/invoice"],
)
export const ProductList = getList(
  ["Sản phẩm", "Loại sản phẩm", "Nhà cung cấp"],
  ["/product", "/type", "/supplier"],
)

