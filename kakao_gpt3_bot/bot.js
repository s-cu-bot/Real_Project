const scriptName = "테스트봇";

var blockedUsers = {}; //차단된 인원
var unblockKey = {"admin1" : 0,  "admin2" : 0}; //차단 해제를 위한 비밀 키 function
var previousConversation = null; //이전 대답
var previousQuestion = null; //이전 질문
var previous_message = []; //이전 메시지 저장용 리스트
  
function getGPTResponse(prompt, previous_request, previous_response) {
  var response = null;
  var message = [{"role" : "user", "content" : prompt}];

  //이전 메시지를 기반으로 대답
  if(previous_request && previous_response) {
    previous_message = previous_message.concat([{"role" : "user", "content" : previous_request},{"role" : "system", "content" : previous_response}]);
    message = previous_message.concat(message);
  }
  else
    previous_message = [];
  
  //GPT_webAPI와 연동하는 소스
  var connection = org.jsoup.Jsoup.connect("https://api.openai.com/v1/chat/completions")
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer " + "**secret-key**")
      .ignoreContentType(true)
      .timeout(500000)
      .requestBody(JSON.stringify({
        model: "gpt-3.5-turbo",
        messages: message,
        max_tokens: 300,
        temperature: 0.5,
        top_p: 1,
        frequency_penalty: 0,
        presence_penalty: 0
      }))
      .post();
      
var body = connection.body();
var doc = org.jsoup.Jsoup.parse(body);
var text = doc.body().text();
var result = JSON.parse(text).choices;

/*response = result[0].text.match(/[^"'₩n]+/)[0];*/
response = result[0].message.content.trim();
previousConversation = response;

return response;
}
 
function response(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
  
  //해당 인원 차단 해제
  if (msg.startsWith("/해제 ")) {  
    var unblockRequest = msg.substring(4); 
    if (sender in unblockKey && unblockRequest in blockedUsers) {
      delete blockedUsers[unblockRequest]; 
      replier.reply(unblockRequest + "님의 차단이 해제되었습니다."); 
    } 
  }

  //해당 인원 차단
  if (msg.startsWith("/차단 ")) {  
    var blockRequest = msg.substring(4); 
    blockedUsers[blockRequest] = { 
      requestCount: 999
    }; 
      replier.reply(blockRequest + "님의 요청이 차단되었습니다."); 
      return 
  } 
  
  //차단된 인원 확인 목적
  if (msg.startsWith("/도비 ") || msg.startsWith("/도비도비 ")) {
    if (sender in blockedUsers) { 
      if (blockedUsers[sender].requestCount >= 3) { 
        replier.reply(sender + "님의 요청이 차단되었습니다. 개발자에게 문의해주세요!"); 
        return 
      }
      var currentTime = new Date().getTime(); 
      if (currentTime - blockedUsers[sender].lastRequestTime < 10000) { 
        blockedUsers[sender].requestCount += 1; 
      } 
      else { 
        blockedUsers[sender].requestCount = 1; 
      } 
      blockedUsers[sender].lastRequestTime = currentTime; 
    } 
    else { 
      blockedUsers[sender] = {
      requestCount: 1, lastRequestTime: new Date().getTime()
      }
    }
  }

  //gpt api 호출
  if(msg.startsWith("/도비 ") || msg.startsWith("/도비도비 ")) { 
    var prompt = msg.substring(4);
    if(prompt.length > 150) {
      replier.reply("최대 100글자까지 질문 가능합니다!");
    }
    else if(msg.startsWith("/도비 ")) //"도비"는 이전 얘기 reset
      var response = getGPTResponse(prompt, null, null);
    else if(msg.startsWith("/도비도비 ")) //"도비도비"는 이전 얘기와 이어서 진행
      var response = getGPTResponse(prompt, previousQuestion, previousConversation);
     replier.reply(response);
  }
  previousQuestion = prompt
}