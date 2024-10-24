import foodsApis from "@/api/foodsApi"
import { useAppSelector } from "@/app/hooks"
import { ItemRes } from "@/components/Common"
import { CustomButton } from "@/components/Custom/CustomButon"
import { RestaurantData } from "@/models/Foods"
import { ChevronLeft, ChevronRight } from "@mui/icons-material"
import { Box, IconButton, Stack } from "@mui/material"
import * as React from "react"
import { useNavigate } from "react-router-dom"
import "swiper/css"
import { Swiper, SwiperSlide } from "swiper/react"
import { SkeletonCustom } from "../../components/Common/Skeleton"

export interface RecommendRestaurantProps {}

export function RecommendRestaurant(props: RecommendRestaurantProps) {
  const [data, setData] = React.useState<RestaurantData[]>([])
  const [isLoading, setIsLoading] = React.useState<boolean>(false)
  const swiperRef = React.useRef<any>(null)
  const navigate = useNavigate()
  const slidePrev = () => {
    if (swiperRef.current && swiperRef.current.swiper) {
      swiperRef.current.swiper.slidePrev()
    }
  }

  const slideNext = () => {
    if (swiperRef.current && swiperRef.current.swiper) {
      swiperRef.current.swiper.slideNext()
    }
  }

  React.useEffect(() => {
    const fetchData = async () => {
      const response = await foodsApis.getRecommendRestaurants()
      if (response?.status) {
        setData(response?.data)
        setIsLoading(true)
      }
    }
    fetchData()
  }, [])
  const { width } = useAppSelector(state=>state.app)

  const handleStore = () => {
    navigate("/store/get-all-store")
  }

  return (
    <>
      <Box
        className="flex"
        sx={{
          margin: `${
            width <= 750
              ? "0 20px"
              : width <= 900
              ? "0 40px"
              : "0px 0px 20px 0px"
          }`,
        }}
      >
        {width > 900 && (
          <Box className="flex items-center justify-center mr-[5px]">
            <IconButton onClick={slidePrev}>
              <ChevronLeft sx={{ width: "40px", height: "40px" }} />
            </IconButton>
          </Box>
        )}
        <Swiper
          modules={[]}
          className="slide-base py-2 "
          style={{ width: "100%" }}
          slidesPerView={
            width <= 450
              ? 1.2
              : width <= 600
              ? 1.7
              : width <= 750
              ? 2.2
              : width <= 900
              ? 2.6
              : width <= 1200
              ? 3
              : 4
          }
          spaceBetween={20}
          allowTouchMove={true}
          ref={swiperRef}
        >
          {!isLoading ? (
            <>
              {[1, 2, 3, 4].map((item, index) => (
                <SwiperSlide key={index}>
                  <SkeletonCustom />
                </SwiperSlide>
              ))}
            </>
          ) : (
            <>
              {data?.map((item) => (
                <SwiperSlide key={item.id}>
                  <ItemRes
                    imgRes={item.imgRes}
                    nameRes={item.restaurantName}
                    distance={item.distance}
                    detail={item.detail}
                    star={item.star}
                    idRes={item.id}
                  />
                </SwiperSlide>
              ))}
            </>
          )}
        </Swiper>
        {width > 900 && (
          <Box className="flex items-center justify-center ml-[5px]">
            <IconButton onClick={slideNext}>
              <ChevronRight sx={{ width: "40px", height: "40px" }} />
            </IconButton>
          </Box>
        )}
      </Box>
      <Stack alignItems="center">
        <Box className="container-base flex justify-center base-pd">
          <CustomButton
            fullWidth
            onClick={() => handleStore()}
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
            Xem thêm cửa hàng
          </CustomButton>
        </Box>
      </Stack>
    </>
  )
}
