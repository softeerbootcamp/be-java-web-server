function emptyChk(){
  var signUpForm = document.question;
  var userId = signUpForm.userId.value;
  var pw = signUpForm.password.value;
  var name = signUpForm.name.value;
  var email = signUpForm.email.value;
  if (!userId || !pw || !name || !email){
    alert("모든 정보를 입력해주세요")
  } else if (!validEmail(email)){
    alert("올바른 이메일을 입력하세요.")
  } else{
    signUpForm.submit();
  }
}

function validEmail(email){
  let regex = new RegExp('[a-z0-9]+@[a-z]+\.[a-z]{2,3}');
  if (regex.test(email)) return true;
  return false;
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};