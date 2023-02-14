import classes from "./CurrentCoin.module.css";
import PaidIcon from '@mui/icons-material/Paid';

const CurrentCoin = () => {

  return (
    <div className={classes.coinBox}>
      &nbsp;
      <PaidIcon />
      <h3 >현재코인 :</h3>
      &nbsp;
      <h3>16</h3>
      &nbsp;
    </div>
  );
};

export default CurrentCoin;
