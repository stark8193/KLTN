import foodsApis from "@/api/foodsApi"
import {useAppSelector} from "@/app/hooks"
import {ItemFood} from "@/components/Common"
import BreadcrumbsCommon from "@/components/Common/Breadcrumbs"
import {VoucherIcon} from "@/components/Icon"
import {StoreDetailData} from "@/models"
import AccessTimeRoundedIcon from "@mui/icons-material/AccessTimeRounded"
import {Box, Grid, Stack, Typography} from "@mui/material"
import * as React from "react"
import {useParams} from "react-router-dom"
import {SkeletonCustom, SkeletonRes} from "../../components/Common/Skeleton"

export interface DetailProps {
}

const DetailStore = (props: DetailProps) => {
  const {idStore} = useParams()
  const [data, setData] = React.useState<StoreDetailData>()
  const [isLoading, setIsLoading] = React.useState<boolean>(false)
  const {width} = useAppSelector(state => state.app)

  React.useEffect(() => {
    const fetchData = async () => {
      if (idStore) {
        const response = await foodsApis.getDetailStore(+idStore)
        if (response?.status) {
          setData(response.data)
          setIsLoading(true)
        }
      }
    }
    fetchData()
    window.scrollTo(0, 0)
  }, [])

  const breadcrumbItems = [
    {name: "Cửa hàng", link: "/store/get-all-store"},
    {name: `${data?.restaurantName}`, link: "/"},
  ]

  return (
    <>
      {data ? (
        <>
          <Box className="bg-white pb-10 border-b-[1px] border-slate-400">
            <div className={'h-[250px] relative overflow-hidden'}>
              <img
                src={data.imgRes}
                className="w-full absolute top-1/2 -translate-y-1/2 object-cover"
                alt=""
              />
            </div>
            <Box className="container-base   base-pd">
              <BreadcrumbsCommon items={breadcrumbItems}/>
              <Stack direction="row" spacing={2}>
                <Box
                  sx={{flex: 2, maxWidth: width > 1000 ? "none" : "600px"}}
                >
                  {!isLoading ? (
                    <>
                      <SkeletonRes/>
                    </>
                  ) : (
                    <>
                      <Typography variant="h4" sx={{fontWeight: 500, mb: 1}}>
                        {data.restaurantName}
                      </Typography>
                      <p className="text-[#676767] text-sm py-1 ">
                        {data.detail}
                      </p>
                      <Box className="flex gap-2 my-2">
                        <Box className="flex items-center justify-center gap-2">
                          <AccessTimeRoundedIcon/>
                          <Typography sx={{fontSize: "14px"}}>
                            {Math.floor(Number(data.distance) * 12)} phút
                          </Typography>
                        </Box>
                        •
                        <Typography
                          sx={{fontSize: "14px"}}
                          className="flex items-center justify-center"
                        >
                          {data.distance} km
                        </Typography>
                        •
                        <Typography
                          sx={{fontSize: "14px"}}
                          className="flex items-center justify-center"
                        >
                          <svg
                            className=" w-4 h-4 mr-2 text-yellow-400"
                            aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg"
                            fill="currentColor"
                            viewBox="0 0 22 20"
                          >
                            <path
                              d="M20.924 7.625a1.523 1.523 0 0 0-1.238-1.044l-5.051-.734-2.259-4.577a1.534 1.534 0 0 0-2.752 0L7.365 5.847l-5.051.734A1.535 1.535 0 0 0 1.463 9.2l3.656 3.563-.863 5.031a1.532 1.532 0 0 0 2.226 1.616L11 17.033l4.518 2.375a1.534 1.534 0 0 0 2.226-1.617l-.863-5.03L20.537 9.2a1.523 1.523 0 0 0 .387-1.575Z"/>
                          </svg>
                          {" "}
                          {data.star}
                        </Typography>
                      </Box>
                      <p className="mt-1">
                        <span className="mr-10 font-medium text-[#676767]">
                          Giờ mở cửa
                        </span>{" "}
                        <span className="text-sm text-[#676767]">
                          Hôm nay {data.timeStart}-{data.timeClose}
                        </span>
                      </p>
                      <div className="flex flex-row item-center gap-3 my-2">
                        <VoucherIcon color="var(--color-df-2)"/>{" "}
                        <span className="text-[var(--color-df-2)]">
                          Nhiều voucher có sẵn
                        </span>
                      </div>
                      <p>Đã bán: {data?.quantitySold || 0}</p>
                    </>
                  )}
                </Box>
                {width > 1000 && (
                  <Box
                    sx={{
                      flex: 1,
                      display: "flex",
                      justifyContent: "flex-end",
                    }}
                  >
                  </Box>
                )}
              </Stack>
            </Box>
          </Box>
          <Box
            className="h-[auto] py-10"
            sx={{backgroundColor: " rgb(240, 242, 245)"}}
          >
            <Box className="container-base base-pd ">
              <Grid container spacing={{xs: 2, sm: 2, md: 3, lg: 3}}>
                {!isLoading ? (
                  <>
                    {[1, 2, 3, 4, 5].map((item, index) => (
                      <Grid
                        item
                        xs={12}
                        sm={4}
                        md={4}
                        lg={3}
                        key={index + item}
                      >
                        <SkeletonCustom/>
                      </Grid>
                    ))}
                  </>
                ) : (
                  <>
                    {data.foodRecommendDtos.map((item) => (
                      <Grid item xs={12} sm={6} md={4} lg={3} key={item.id}>
                        <ItemFood
                          idFood={item.id}
                          detail={item.detail}
                          imgFood={item.imgFood}
                          idRes={item.restaurantEntityId}
                          toppingList={data.toppingEntityList}
                          foodName={item.foodName}
                          price={item.price}
                          distance={item.distance || 0}
                          qSold={item.quantityPurchased || 0}
                          nameStore={item.nameRestaurantFood}
                          idStore={item.restaurantEntityId}
                          typeFoodEntityId={item.typeFoodEntityId}
                        />
                      </Grid>
                    ))}
                  </>
                )}
              </Grid>
            </Box>
          </Box>
        </>
      ) : (
        ""
      )}
    </>
  )
}

export default DetailStore
