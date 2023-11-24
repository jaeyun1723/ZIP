const getDealDate = (deal) => {
  const year = String(deal.dealYear % 100).padStart(2, '0');
  const month = String(deal.dealMonth).padStart(2, '0');
  const day = String(deal.dealDay).padStart(2, '0');

  return `${year}.${month}.${day}`;
};

export default getDealDate;
