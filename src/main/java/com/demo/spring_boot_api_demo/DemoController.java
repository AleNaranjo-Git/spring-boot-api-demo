package com.demo.spring_boot_api_demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(value = "/demo", produces = MediaType.TEXT_HTML_VALUE)
    public String demo() {
        return """
        <!doctype html>
        <html>
        <body>
        <h3>OIDC Authorization Demo</h3>

        <button onclick="login('userA')">Login User A</button>
        <button onclick="login('userB')">Login User B</button>
        <br><br>
        <button onclick="callApi('/resource1')">Resource 1</button>
        <button onclick="callApi('/resource2')">Resource 2</button>

        <pre id="out"></pre>

        <script>
        let token=null;
        async function login(u){
        const r=await fetch('/token?username='+u);
        const j=await r.json();
        token=j.access_token;
        document.getElementById('out').textContent=token;
        }
        async function callApi(p){
        const r=await fetch(p,{headers:{Authorization:'Bearer '+token}});
        document.getElementById('out').textContent=await r.text();
        }
        </script>
        </body>
        </html>
        """;
    }
}