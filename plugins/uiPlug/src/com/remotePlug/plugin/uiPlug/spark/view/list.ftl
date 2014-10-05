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
                <#if helper.isAFile(item) >
                    <td>${item.getFormat()}</td>
                    <td><input type="submit"/></td>
                <#else>
                    <td> DIR </td>
                    <td> ======= </td>
                </#if>

                <input type="hidden" value="${item.getId()}" name="id" />

            </tr>
        </form>
    </#list>

</table>
