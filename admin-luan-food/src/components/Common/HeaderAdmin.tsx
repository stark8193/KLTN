import {useInforUser} from "@/hooks"
import {ArrowBackIosNew, StorageOutlined,} from "@mui/icons-material"
import {Avatar, Stack} from "@mui/material"
import {useState} from "react"
import {useNavigate} from "react-router-dom"
import {MenuAdmin} from "."
import {ProductList} from "@/constants"

export function HeaderAdmin() {
  const [openOrder, setOpenOrder] = useState(false)
  const [openInvoice, setOpenInvoice] = useState(false)
  const [openReport, setOpenReport] = useState(false)
  const [openProduct, setOpenProduct] = useState(false)
  const [openConfig, setOpenConfig] = useState(false)

  const user = useInforUser()
  const [hovered, setHovered] = useState(false)
  const navigate = useNavigate()
  const handleMoveHome = () => {
    navigate("/", {replace: true})
  }

  return (
    <Stack
      direction="row"
      className="p-[10px] border-b bg-[var(--color-layer-2)] border-gray-300 "
      justifyContent="space-between "
    >
      <Stack direction="row" alignItems="center">
        <Stack
          direction="row"
          alignItems={"center"}
          onMouseEnter={() => setHovered(true)}
          onMouseLeave={() => setHovered(false)}
          onClick={handleMoveHome}
          sx={{
            cursor: "pointer",
            margin: "0 5px",

            "&:hover": {
              transform: " translateX(-5px)",
              transition: "all 0.3s",
            },
          }}
        >
          <span className="mr-3 text-white">
            {hovered ? <ArrowBackIosNew/> : <StorageOutlined/>}
          </span>
          <span className="font-medium mr-4 text-white">Trang quản trị</span>
        </Stack>

        <div className="relative">
          <button
            onClick={() => navigate('/admin/customer')}

            className="text-white translate-y-[1.5px] hover:bg-gray-500 text-[14px]  py-1 px-3  hover:border-gray-300 rounded mr-1"
          >
            Khách hàng
          </button>
        </div>

        <div className="relative">
          <button
            onClick={() => navigate('/admin/invoice')}
            className="text-white translate-y-[1.5px] hover:bg-gray-500  text-[14px]  py-1 px-3  hover:border-gray-300 rounded mr-1"
          >
            Hóa đơn
          </button>
        </div>
        <div className="relative">
          <button
            onClick={() => setOpenProduct(true)}
            className="text-white translate-y-[1.5px] hover:bg-gray-500  text-[14px]  py-1 px-3  hover:border-gray-300 rounded mr-1"
          >
            Sản phẩm
          </button>
          <MenuAdmin
            open={openProduct}
            setOpen={setOpenProduct}
            items={ProductList}
          />
        </div>

        <div className="relative">
          <button
            onClick={() => navigate('/admin/voucher')}
            className="text-white translate-y-[1.5px] hover:bg-gray-500  text-[14px]  py-1 px-3  hover:border-gray-300 rounded mr-1"
          >
            Ưu đãi
          </button>

        </div>
      </Stack>
      <Stack direction="row">

        <Avatar
          src={user?.imgUser}
          className="ml-3"
          sx={{borderRadius: "6px", cursor: "pointer"}}
        />
      </Stack>
    </Stack>
  )
}
