import { useForm } from 'react-hook-form'
import { useSelector} from 'react-redux'
// import { useNavigate } from 'react-router-dom'
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import { useEffect, useState } from 'react';
import { getAccount }  from 'api/user'

const schema = yup
  .object({
      password: yup
      .string()
      .min(8, "최소 8자 이상 작성해야 합니다.")
      .max(16, "최대 16자까지 작성 가능합니다.")
      .matches(
      /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{7,15}$/,
      "비밀번호는 영어, 숫자, 특수문자만 가능합니다.")
      .required("비밀번호를 입력해 주세요!"),

      checkPassword: yup
      .string()
      .oneOf([yup.ref('password'), null], '비밀번호가 일치하지 않습니다')
      .required('비밀번호를 한번 더 입력해 주세요'),

      name: yup
      .string()
      .min(4, "최소 2자 이상 작성해야 합니다.")
      .max(12, "최대 12자까지 작성 가능합니다.")
      .matches(
        /^[A-Za-z0-9가-힣]{1,11}$/,
        "닉네임은 영어, 한글, 숫자만 가능합니다."
      )
      .required(),

      email: yup
      .string()
      .matches(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i, "이메일을 정확히 입력해 주세요.")
      .required('이메일을 입력해 주세요.'),
      
      tel: yup
      .string()
      .matches(/^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/, "번호를 정확히 입력해 주세요.")
    })
  .required();

const ChangeAccount = () => {
    const { loading } = useSelector((state) => state.auth)
    // const navigate = useNavigate()
    const [defaultValues, setDefaultValues] = useState();

    const {
        register,
        handleSubmit,
        formState: { errors },
      } = useForm({
        mode: "onChange",
        resolver: yupResolver(schema),
      });


    const submitForm = (data) => {
        console.log(data)
        data.email = data.email.toLowerCase()
        const { checkPassword, memberId, ...form } = data
        // dispatch(registerUser(form))
      }
    
    const deleteHandler = async () => {
        console.log('삭제 요청')
    }

    useEffect(() => {
      const getData = async () => {
        await getAccount()
          .then((res) => {
            setDefaultValues(res.data.value)
          })
      }
      getData()
    }, [])

    return (
        <div>
            <h1>회원정보 수정</h1>
                <form onSubmit={handleSubmit(submitForm)}>
                    {/* {error && <Error>{error}</Error>} */}
                    <div className='form-group'>
                      <label htmlFor='memberId'>아이디</label>
                      <input
                        disabled={true}
                        defaultValue={defaultValues?.memberId}
                        {...register('memberId')}
                      />
                    </div>
                    <p>{errors.memberId?.message}</p>
                    <div className='form-group'>
                      <label htmlFor='password'>비밀번호</label>
                      <input
                        type='password'
                        {...register('password')}
                      />
                    </div>
                    <p>{errors.password?.message}</p>
                    <div className='form-group'>
                      <label htmlFor='checkPassword'>비밀번호 확인</label>
                      <input
                        type='password'
                        {...register('checkPassword')}
                      />
                    </div>
                    <p>{errors.checkPassword?.message}</p>
                    <div className='form-group'>
                      <label htmlFor='email'>이메일</label>
                      <input
                        {...register('email')}
                        defaultValue={defaultValues?.email}
                      />
                    </div>
                    <p>{errors.email?.message}</p>
                    <div className='form-group'>
                      <label htmlFor='name'>닉네임</label>
                      <input
                        {...register('name')}
                        defaultValue={defaultValues?.name}
                      />
                    </div>
                    <p>{errors.name?.message}</p>
                    <div className='form-group'>
                      <label htmlFor='tel'>핸드폰</label>
                      <input
                        {...register('tel')}
                        defaultValue={defaultValues?.tel}
                      />
                    </div>
                    <p>{errors.tel?.message}</p>
                    <button type='submit' className='button' disabled={loading}>
                      {loading ? '대기중' : '수정'}
                      {/* <Spinner /> */}
                    </button>
                </form>
            <button onClick = {deleteHandler}>탈퇴</button>
        </div>
    )
}

export default ChangeAccount