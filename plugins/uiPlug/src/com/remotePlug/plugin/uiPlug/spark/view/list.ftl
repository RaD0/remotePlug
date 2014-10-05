<h1> The List! </h1>

<table>
    <tr>
        <th>Name</th>
        <th>Format</th>
    </tr>

    <#list items as item>
        <form action="/play" method="post">
            <tr>
                <td>${item.getName()}</td>
                <td>${item.getFormat()}</td>
                <input type="hidden" value="${item.getId()}" name="id" />
                <td><input type="submit"/></td>
            </tr>
        </form>
    </#list>

</table>
