export const SupplierFields= [
  {
    label: "Tên nhà cung cấp",
    key: "restaurantName",
    fieldType: {
      type: "input",
    },
    example: "Cửa hàng abc",
    validations: [
      {
        rule: "required",
        errorMessage: "Name is required",
        level: "error",
      },
    ],

  },
  {
    label: "Tên nhà cung cấp",
    key: "imgRes",
    fieldType: {
      type: "input",
    },
    example: "Cửa hàng abc",
    validations: [
      {
        rule: "required",
        errorMessage: "Name is required",
        level: "error",
      },
    ],

  },
  {
    label: "Giờ mở cửa",
    key: "timeStart",
    fieldType: {
      type: "input",
    },
    example: "8:00",
  },
  {
    label: "Giờ đóng cửa",
    key: "timeClose",
    fieldType: {
      type: "input",
    },
    example: "22:00",
  },
  {
    label: "Khoảng cách",
    key: "distance",
    fieldType: {
      type: "input",
    },
    example: "1.1 km",
  },
  {
    label: "Số điện thoại",
    key: "phoneNumber",
    fieldType: {
      type: "input",
    },
    example: "09xxxxxxx",
  },
  {
    label: "Địa chỉ",
    key: "address",
    fieldType: {
      type: "input",
    },
    example: "Đường abc, ngõ xyz, TP.Hà Nội",
  },
] as const
