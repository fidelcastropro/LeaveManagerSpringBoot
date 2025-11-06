const express = require('express')
const app = express()
const {join} = require('path')
const PORT = 3300

app.use(express.static(join(__dirname,'./sources')))
app.use('/loginpage',express.static(join(__dirname,'./sources')))
app.use('/loginpage/dayscholar/studentacceptedrequests',express.static(join(__dirname,'./sources')))
app.use('/loginpage/dayscholar/studentpendingrequests',express.static(join(__dirname,'./sources')))
app.use('/loginpage/hosteller/studentacceptedrequests',express.static(join(__dirname,'./sources')))
app.use('/loginpage/hosteller/studentpendingrequests',express.static(join(__dirname,'./sources')))
app.use('/loginpage/facultygreeting',express.static(join(__dirname,'./sources')))
app.use('/loginpage/facultygreeting/facultygrantedapprovals',express.static(join(__dirname,'./sources')))
app.use('/loginpage/facultygreeting/facultypendingapprovals/',express.static(join(__dirname,'./sources')))
app.use('/loginpage/hodgreeting/hodgrantedapprovals',express.static(join(__dirname,'./sources')))
app.use('/loginpage/hodgreeting/hodpendingapprovals',express.static(join(__dirname,'./sources')))
app.use('/loginpage/wardengreeting/wardenpendingapprovals',express.static(join(__dirname,'./sources')))
app.use('/loginpage/wardengreeting/wardengrantedapprovals',express.static(join(__dirname,'./sources')))

app.get('^/$|/loginpage(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'loginpage.html'))
})

app.get('^/$|/loginpage/hostellerformselection(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'hostellerformselection.html'))
})

app.get('^/$|/loginpage/dayscholarformselection(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'dayscholarformselection.html'))
})

app.get('^/$|/loginpage/successfulsubmission(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'successfulsubmission.html'))
})

app.get('^/$|/loginpage/hosteller/studentpendingrequests(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'pendingrequests.html'))
})

app.get('^/$|/loginpage/dayscholar/studentpendingrequests(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'pendingrequests.html'))
})

app.get('^/$|/loginpage/hosteller/studentacceptedrequests(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'acceptedrequests.html'))
})

app.get('^/$|/loginpage/dayscholar/studentacceptedrequests(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'acceptedrequests.html'))
})

app.get('^/$|/loginpage/hosteller/studentacceptedrequests/leaveform1(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'leaveform.html'))
})

app.get('^/$|/loginpage/hosteller/studentacceptedrequests/leaveform2(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'outingform.html'))
})

app.get('^/$|/loginpage/hosteller/studentacceptedrequests/leaveform3(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'hostelleave.html'))
})

app.get('^/$|/loginpage/dayscholar/studentacceptedrequests/leaveform1(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'dayscholarlinks.html'))
})

app.get('^/$|/loginpage/dayscholar/studentacceptedrequests/leaveform2(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'dayscholarlinks2.html'))
})

app.get('^/$|/loginpage/facultygreeting(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'facultygreeting.html'))
})

app.get('^/$|/loginpage/facultygreeting/facultygrantedapprovals(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'facultygrantedapprova.html'))
})

app.get('^/$|/loginpage/facultygreeting/facultygrantedapprovals/leaveform1(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'facultylinks.html'))
})

app.get('^/$|/loginpage/facultygreeting/facultygrantedapprovals/leaveform2(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'facultylinks2.html'))
})


app.get('^/$|/loginpage/facultygreeting/facultypendingapprovals(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'pendingapprovals.html'))
})

app.get('^/$|/loginpage/hodgreeting(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'hodgreeting.html'))
})

app.get('^/$|/loginpage/hodgreeting/hodgrantedapprovals(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'hodgrantedapprovals.html'))
})

app.get('^/$|/loginpage/hodgreeting/hodgrantedapprovals/leaveform1(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'hodlinks.html'))
})

app.get('^/$|/loginpage/hodgreeting/hodgrantedapprovals/leaveform2(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'hodlinks2.html'))
})


app.get('^/$|/loginpage/hodgreeting/hodpendingapprovals(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'hodpendingapprovals.html'))
})

app.get('^/$|/loginpage/wardengreeting(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'wardengreeting.html'))
})

app.get('^/$|/loginpage/wardengreeting/wardengrantedapprovals(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'wardengrantedapprovals.html'))
})

app.get('^/$|/loginpage/wardengreeting/wardengrantedapprovals/leaveform1(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'wardenlinks.html'))
})

app.get('^/$|/loginpage/wardengreeting/wardengrantedapprovals/leaveform2(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'wardenlinks2.html'))
})

app.get('^/$|/loginpage/wardengreeting/wardengrantedapprovals/leaveform3(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'wardenlinks3.html'))
})

app.get('^/$|/loginpage/wardengreeting/wardenpendingapprovals(.html)?',(req,res)=>{
    res.sendFile(join(__dirname,'wardenpendingapprovals.html'))
})

app.get('/*',(req,res)=>{
    res.status(404).sendFile(join(__dirname,'error.html'))
})



app.listen(PORT,(err)=>{
    if(err){
        console.log(err.message)
    }
    else{
        console.log(`Server is Running on ${PORT}`)
    }
})