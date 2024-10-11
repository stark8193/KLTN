import {useAppSelector} from "@/app/hooks"
import {CustomButton} from "@/components/Custom/CustomButon"
import {Box, Paper, Stack} from "@mui/material"
import classNames from "classnames"
import {useSnackbar} from "notistack"
import React from "react"
import {useNavigate} from "react-router-dom"
import classes from "./styles.module.css"

export interface SearchFoodProps {
}

export function SearchFood(props: SearchFoodProps) {
  const {width} = useAppSelector(state => state.app)
  const navigate = useNavigate()
  const {enqueueSnackbar} = useSnackbar()
  const [search, setSearch] = React.useState<string>("")
  const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") handleSearch()
  }

  const handleSearch = () => {
    if (search) navigate(`/search?key=${search}`)
    else
      enqueueSnackbar("Bạn chưa nhập gì để tìm kiếm !", {variant: "warning"})
  }

  return (
    <Box
      className={classNames({
        [classes.mainSearch]: width > 750,
        "base-pd": true,
      })}
    >
      <Paper
        elevation={width > 750 ? 2 : 0}
        className={classNames({
          [classes.container]: width > 750,
          [classes.dsMobile]: width <= 750,
        })}
      >
        <Stack
          direction={"column"}
          sx={{height: "100%"}}
          justifyContent="space-between"
        >
          <Box>
            <p className={classes["tx-1"]}>Bạn muốn ăn gì bây giờ?</p>
          </Box>

          <Box sx={{mb: 1, mt: 1, postion: "relative"}}>

            <input
              onKeyDown={handleKeyPress}
              onChange={(e) => setSearch(e.target.value)}
              className="appearance-none border-2 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
              id="search-food"
              style={{
                height: "40px",
                borderRadius: "6px",
                border: "1px solid var(--color-df-1)",
              }}
              type="text"
              placeholder="Tìm kiếm món ăn"
            />
            <CustomButton
              onClick={() => handleSearch()}
              fullWidth
              sx={{
                background: "var(--color-df-1)",
                color: "white",
                mt: "10px",
                borderRadius: "6px",
                fontSize: "15px",
                height: "40px",
                fontWeight: "600",
                textTransform: "unset",

                "&:hover": {
                  background: "var(--color-df-1)",
                  color: "white",
                },
              }}
            >
              Tìm kiếm
            </CustomButton>
          </Box>
        </Stack>
      </Paper>
    </Box>
  )
}
