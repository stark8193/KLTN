import {useAppDispatch, useAppSelector} from "@/app/hooks"
import {InputField, PasswordField} from "@/components/FormControls"
import {LoginForm} from "@/models"
import {yupResolver} from "@hookform/resolvers/yup"
import {ArrowBack} from "@mui/icons-material"
import {Box, Button, IconButton, LinearProgress, Paper, Stack, Typography,} from "@mui/material"
import {useSnackbar} from "notistack"
import {useEffect} from "react"
import {FormProvider, SubmitHandler, useForm} from "react-hook-form"
import {Link, useNavigate} from "react-router-dom"
import * as yup from "yup"
import {authActions} from "../AuthSlice"

export interface LoginPageProps {
}

export function LoginPage(props: LoginPageProps) {
  const logging = useAppSelector((state) => state.auth.logging)
  const actionAuth = useAppSelector((state) => state.auth.actionAuth)
  const {enqueueSnackbar} = useSnackbar()
  const {width} = useAppSelector(state => state.app)
  const dispatch = useAppDispatch()
  const schema = yup.object().shape({
    username: yup.string().required("Cần nhập mã người dùng"),
    password: yup.string().required("Cần nhập mật khẩu"),
  })

  const form = useForm<LoginForm>({
    resolver: yupResolver(schema),
  })
  const handleLogin: SubmitHandler<LoginForm> = (data) => {
    dispatch(authActions.login(data))
  }
  useEffect(() => {
    if (actionAuth == "Failed") {
      enqueueSnackbar("Mã người dùng hoặc mật khẩu không chính xác", {
        variant: "error",
      })
    }
  }, [actionAuth])

  useEffect(() => {
    document.body.style.overflow = "hidden"

    return () => {
      document.body.style.overflow = "hidden scroll"
    }
  }, [])
  const navigate = useNavigate()
  const handleHome = () => {
    navigate("/")
  }
  return (
    <div className="container-cs w-screen relative h-screen flex items-center justify-center">
      <IconButton
        onClick={handleHome}
        sx={{position: "absolute"}}
        className="top-[15px] left-[15px]"
      >
        <ArrowBack htmlColor="white"/>
      </IconButton>
      {logging && (
        <LinearProgress
          sx={{position: "fixed", top: "0px", left: "0px", width: "100%"}}
        />
      )}
      <Paper
        elevation={8}
        sx={{
          zIndex: 5,
          maxWidth: "1000px",
          maxHeight: "500px",
          width: "90%",
          height: "70%",
          backgroundColor: "white",
          borderRadius: "8px",
        }}
      >
        <Stack
          sx={{width: "100%", height: "100%", position: "relative"}}
          flexDirection={"row"}
        >
          {width > 900 && (
            <Stack
              sx={{
                width: "50%",
                height: "100%",
                backgroundColor: "#1B1B1E",
                p: 2,
                opacity: "1",
                borderTopLeftRadius: "8px",
                borderBottomLeftRadius: "8px",
              }}
              flexDirection={"row"}
              alignItems={"center"}
              justifyContent={"center"}
            >
              <img
                style={{width: "60%"}}
                src="/assets/luan_food.svg"
                alt="logo-luan-food"
              />
            </Stack>
          )}

          <Stack sx={{flex: "1 1", position: "relative"}}>
            <Box
              sx={{
                padding: "0 20px 0 20px",
                width: "100%",
                margin: "0 auto",
                mt: "50px",
              }}
            >
              <Box sx={{marginBottom: "20px"}}>
                <Typography variant="h4" sx={{fontWeight: 500}}>
                  Đăng nhập
                </Typography>
                <span style={{color: "rgb(122, 122, 122)"}}>
                  Hãy đăng nhập để tiếp tục
                </span>
              </Box>
              <FormProvider {...form}>
                <form
                  style={{display: "flex", flexDirection: "column"}}
                  onSubmit={form.handleSubmit(handleLogin)}
                >
                  <InputField label="Mã người dùng" name="username"/>
                  <PasswordField label="Mật khẩu" name="password"/>

                  <Button
                    size="large"
                    sx={{marginTop: 1,}}
                    variant="contained"
                    type="submit"
                    disabled={logging}
                  >
                    Đăng nhập
                  </Button>
                </form>
              </FormProvider>
              <Typography sx={{textAlign: "center", marginTop: "10px"}}>
                Chưa có tài khoản?{" "}
                <Link style={{color: "blue"}} to="/register">
                  Đăng kí
                </Link>
              </Typography>
            </Box>
            <Stack
              justifyContent={"space-between"}
              flexDirection={"row"}
              sx={{
                position: "absolute",
                bottom: "10px",
                width: "100%",
                padding: "0 20px 0px 20px",
              }}
            >
              <Link style={{color: "blue"}} to="/forgot">
                Quên mật khẩu?
              </Link>

              <Typography variant="body2" color="text.secondary">
                Form by{" "}
                <Link
                  color="inherit"
                  style={{textDecoration: "underline"}}
                  to="https://github.com/stark8193"
                >
                  @stark8193
                </Link>{" "}
                {new Date().getFullYear()}
              </Typography>
            </Stack>
          </Stack>
        </Stack>
      </Paper>
    </div>
  )
}
