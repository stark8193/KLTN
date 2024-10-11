import foodsApis from "@/api/foodsApi"
import {useAppSelector} from "@/app/hooks"
import {CustomButton} from "@/components/Custom/CustomButon"
import {foodData} from "@/models"
import {Box, Stack} from "@mui/material"
import * as React from "react"
import {useNavigate} from "react-router-dom"
import "swiper/css"
import SliderItemRecommend from "./SliderItemRecommend"

export interface RecommendFoodProps {
}

export function RecommendFood(props: RecommendFoodProps) {
  const [data, setData] = React.useState<foodData[]>([])
  const [isLoading, setIsLoading] = React.useState<boolean>(false)
  const swiperRef = React.useRef<any>(null)
  const navigate = useNavigate()

  React.useEffect(() => {
    const fetchData = async () => {
      const response = await foodsApis.getRecommendFoods()
      if (response?.status) {
        setData(response?.data)
        setIsLoading(true)
      }
    }
    fetchData()
  }, [])
  const {width} = useAppSelector(state => state.app)

  //

  return (
    <>
      <Box
        className="flex"
        sx={{
          margin: `${
            width <= 750
              ? "0 20px 24px 20px"
              : width <= 900
                ? "0 40px 24px 40px"
                : "0px 0px 24px 0px"
          }`,
        }}
      >
        <div className='grid grid-cols-4 gap-6 base-pd base-tx'>
          {data?.map((item) => (
            <SliderItemRecommend {...item} />
          ))}
        </div>
      </Box>
      <Stack alignItems="center">
        <Box className="container-base pb-10 flex justify-center base-pd">
          <CustomButton
            fullWidth
            onClick={() => navigate("/store/get-all-food")}
            sx={{
              width: "350px",
              border: "1px solid var(--color-df-1)",
              color: "var(--color-df-1)",
              mt: "15px",
              mb: "20px",
              borderRadius: "6px",
              fontSize: "14px",
              height: "45px",
              fontWeight: "600",
              textTransform: "unset",
              transition: "all 0.2s",
              "&:hover": {
                border: "1px solid var(--color-df-1)",
                background: "white",
                color: "var(--color-df-1)",
              },
            }}
          >
            Xem thêm món ăn
          </CustomButton>
        </Box>
      </Stack>
    </>
  )
}
